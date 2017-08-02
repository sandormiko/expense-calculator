package expense.calculator.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

import expense.calculator.domain.Expense;
import io.dropwizard.hibernate.AbstractDAO;

public class ExpenseDAO extends AbstractDAO<Expense> {

	@Inject
	public ExpenseDAO(SessionFactory sessionFactory) {
		super(sessionFactory);

	}

	public Expense persist(Expense expense) {
		return super.persist(expense);
	}

	public List<Expense> findAll() {
		return list(currentSession().createQuery("select e from Expense e", Expense.class));
	}

}
