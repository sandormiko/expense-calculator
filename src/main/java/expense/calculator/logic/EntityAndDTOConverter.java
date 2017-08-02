package expense.calculator.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

import expense.calculator.domain.Expense;
import expense.calculator.domain.ExpenseDTO;

public class EntityAndDTOConverter {

	public static Function<Expense, ExpenseDTO> toDTO = new Function<Expense, ExpenseDTO>() {
		public ExpenseDTO apply(Expense entity) {
			ExpenseDTO dto = new ExpenseDTOBuilder()
									.setId(entity.getId())
									.setAmount(entity.getAmount())
									.setDate(entity.getCreationDate())
									.setReason(entity.getReason())
									.setVat(entity.getVat())
									.build();
			return dto;
		}
	};

	public static Function<ExpenseDTO, Expense> toEntity = new Function<ExpenseDTO, Expense>() {
		public Expense apply(ExpenseDTO dto) {
			Expense expense = new Expense();
			expense.setAmount(dto.amount);
			expense.setReason(dto.reason);
			expense.setCreationDate(dto.date);
			expense.setVat(dto.vat);
			//Now the client provides vat
			if(dto.vat == null){
				BigDecimal amountWithoutVat = dto.amount.divide(BigDecimal.valueOf(1.27),2,RoundingMode.HALF_UP);
				expense.setVat(dto.amount.subtract(amountWithoutVat));
			}
			return expense;
		}
	};
}
