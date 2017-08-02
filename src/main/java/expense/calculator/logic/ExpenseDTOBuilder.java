package expense.calculator.logic;
import java.math.BigDecimal;
import java.util.Date;

import expense.calculator.domain.ExpenseDTO;

public class ExpenseDTOBuilder {

	private Long id;
	private String reason;
	private Date date;
	private BigDecimal amount;
	private BigDecimal vat;

	public ExpenseDTOBuilder setId(Long id) {
		this.id = id;
		return this;
	}
	
	public ExpenseDTOBuilder setReason(String reason) {
		this.reason = reason;
		return this;
	}

	public ExpenseDTOBuilder setDate(Date date) {
		this.date = date;
		return this;
	}

	public ExpenseDTOBuilder setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public ExpenseDTOBuilder setVat(BigDecimal vat) {
		this.vat = vat;
		return this;
	}
	
	public ExpenseDTO build() {
	      return new ExpenseDTO(id,reason, date,amount, vat);
	   }
}
