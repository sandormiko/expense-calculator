package expense.calculator.resource.end2end;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


import expense.calculator.dao.ExpenseDAO;
import expense.calculator.domain.Expense;
import expense.calculator.logic.ExpenseDTOBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import expense.calculator.domain.ExpenseDTO;
import expense.calculator.matchers.ExpenseDTOMatcher;
import expense.calculator.resource.ExpensesResource;

public class ExpensesEndToEndTest {

	private static final String H2_URl = "jdbc:h2:./db/expenses;MODE=PostgreSQL;INIT=runscript from 'classpath:create-db.sql'";
	private static final String H2_USER = "sa";
	private static final String H2_DRIVER = "org.h2.Driver";
	private static final String SESSION_CONTEXT_CLASS = "thread";
	private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	
	private static SessionFactory sessionFactory;
	
	private ExpensesResource expensesRS;
	private ExpensesDriver driver;

	@BeforeClass
	public static void setupClass() {
		prepareSessionFactory();

	}

	
	@Before
	public void setup() {
		ExpenseDAO expenseDAO = new ExpenseDAO(sessionFactory);
		expensesRS = new ExpensesResource(expenseDAO);
		driver = new ExpensesDriver(expensesRS);
		sessionFactory.openSession();
		sessionFactory.getCurrentSession().beginTransaction();
	}

	@After
	public void close() {

		sessionFactory.getCurrentSession().getTransaction().rollback();
		sessionFactory.getCurrentSession().close();

	}
	
	@AfterClass
	public static void tearDown() {

		sessionFactory = null;
	}

	@Test
	public void saveAndQueryExpensesTest() {
		
		Date today = Calendar.getInstance().getTime();
		ExpenseDTO anExpense = new ExpenseDTOBuilder().setDate(today)
                                                            .setAmount(BigDecimal.valueOf(200D))
                                                            .setReason("A reason")
                                                            .setVat(BigDecimal.valueOf(33.33))
                                                            .build();
		
		driver.queryAllTheExpenses()
			  .thereAreNoExpenses()
			  .insertAnExpense(anExpense)
			  .queryAllTheExpenses()
			  .theNumberOfExpensesIs(1)
			  .expensesContain(
					  new ExpenseDTOMatcher()
					  .amount(anExpense.amount)
					  .reason(anExpense.reason)
					  .date(anExpense.date)
					  .vat(anExpense.vat));

		ExpenseDTO anotherExpense = new ExpenseDTOBuilder().setDate(DateUtils.addDays(today, -1))
                                                                .setAmount(BigDecimal.valueOf(100D))
                                                                .setReason("Another reason")
                                                                .setVat(BigDecimal.valueOf(16.67))
                                                                .build();
		
		
		driver.insertAnExpense(anotherExpense)
			  .queryAllTheExpenses()
			  .theNumberOfExpensesIs(2)
			  .expensesContain(
				  new ExpenseDTOMatcher()
				  	.amount(anExpense.amount)
					.reason(anExpense.reason)
					.date(anExpense.date)
					.vat(anExpense.vat),
					new ExpenseDTOMatcher()
				  	.amount(anotherExpense.amount)
					.reason(anotherExpense.reason)
					.date(anotherExpense.date)
					.vat(anotherExpense.vat));
		
	}
	
	
	private static void prepareSessionFactory() {
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.url", H2_URl);
		config.setProperty("hibernate.connection.username", H2_USER);
		config.setProperty("hibernate.connection.password", StringUtils.EMPTY);
		config.setProperty("hibernate.connection.driver_class", H2_DRIVER);
		config.setProperty("hibernate.current_session_context_class", SESSION_CONTEXT_CLASS);
		config.setProperty("hibernate.dialect", HIBERNATE_DIALECT);
		config.addAnnotatedClass(Expense.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties())
				.build();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}
}
