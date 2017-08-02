package expense.calculator.resource;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import expense.calculator.dao.ExpenseDAO;
import expense.calculator.domain.ExpenseDTO;
import expense.calculator.logic.EntityAndDTOConverter;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/expenses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpensesResource {

	private final ExpenseDAO expensesDAO;

	@Inject
	public ExpensesResource(ExpenseDAO expensesDAO) {
		this.expensesDAO = expensesDAO;
	}

	@GET
	@UnitOfWork(readOnly = true, transactional = false)
	public Response getExpenses() {

		return Response.ok(expensesDAO.findAll().stream().map(EntityAndDTOConverter.toDTO).collect(Collectors.<ExpenseDTO>toList())).build();
	}

	@POST
	@UnitOfWork
	public Response saveExpense(ExpenseDTO expenseDTO, @Context UriInfo uriInfo) {
		Long resourceId = expensesDAO.persist(EntityAndDTOConverter.toEntity.apply(expenseDTO)).getId();
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(resourceId));
		return Response.created(builder.build()).build();
	}

}
