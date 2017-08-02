package expense.calculator.resource.end2end;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;

import expense.calculator.domain.ExpenseDTO;
import expense.calculator.matchers.ExpenseDTOMatcher;
import expense.calculator.resource.ExpensesResource;

public class ExpensesDriver {

	private final ExpensesResource expensesRs;
	private List<ExpenseDTO> expenses;
	private final UriInfo uriInfo;
	
	public ExpensesDriver(ExpensesResource expensesRs) {
		this.expensesRs = expensesRs;
		uriInfo = prepareUriInfo();
	}

	@SuppressWarnings("unchecked")
	public ExpensesDriver queryAllTheExpenses() {
		expenses = (List<ExpenseDTO>) expensesRs.getExpenses().getEntity();
		return this;
	}
	
	public ExpensesDriver theNumberOfExpensesIs(Integer nrOfExpenses) {
		assertThat(expenses.size(),equalTo(nrOfExpenses));
		return this;
	}
	
	public ExpensesDriver thereAreNoExpenses() {
		assertThat(expenses.size(),equalTo(0));
		return this;
	}
	
	public ExpensesDriver insertAnExpense(ExpenseDTO expense) {
		expensesRs.saveExpense(expense, uriInfo);
		return this;
	}
	
	public ExpensesDriver expensesContain(ExpenseDTOMatcher ...matchers) {
		assertThat(expenses, containsInAnyOrder(matchers));
		return this;
	}
	
	private UriInfo prepareUriInfo(){
		final String localhost = "http://localhost";
		ContainerRequest cr = new ContainerRequest(URI.create(localhost),
                                                        URI.create(localhost),
                                                        "POST",null, 
                                                        new MapPropertiesDelegate());
		return new UriRoutingContext(cr);
	}

}
