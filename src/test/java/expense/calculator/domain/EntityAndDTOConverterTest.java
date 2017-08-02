package expense.calculator.domain;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import expense.calculator.logic.EntityAndDTOConverter;
import expense.calculator.logic.ExpenseDTOBuilder;
import expense.calculator.matchers.ExpenseMatcher;
import org.junit.Test;

import expense.calculator.matchers.ExpenseDTOMatcher;

public class EntityAndDTOConverterTest {

	@Test
	public void testEntityToDTO() {
		Date today = Calendar.getInstance().getTime();
		Expense entity = new Expense();
		entity.setAmount(BigDecimal.valueOf(100D));
		entity.setVat(BigDecimal.valueOf(16.67));
		entity.setReason("A reason");
		entity.setCreationDate(today);

		ExpenseDTO dto = EntityAndDTOConverter.toDTO.apply(entity);

		assertThat(dto, new ExpenseDTOMatcher()
				.amount(entity.getAmount())
				.date(entity.getCreationDate())
				.reason(entity.getReason())
				.vat(entity.getVat()));

	}

	@Test
	public void testDTOToEntity() {
		Date today = Calendar.getInstance().getTime();
		ExpenseDTO dto = new ExpenseDTOBuilder()
                                            .setDate(today)
                                            .setAmount(BigDecimal.valueOf(200D))
                                            .setReason("A reason")
                                            .setVat(BigDecimal.valueOf(33.33)).build();

		Expense entity = EntityAndDTOConverter.toEntity.apply(dto);
		assertThat(entity, new ExpenseMatcher()
                                            .amount(dto.amount)
                                            .date(dto.date)
                                            .reason(dto.reason)
                                            .vat(dto.vat));

	}
}
