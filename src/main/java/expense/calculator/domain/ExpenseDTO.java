package expense.calculator.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import expense.calculator.jackson.DateDeserializer;

public class ExpenseDTO {

	public Long id;
	public String reason;
	@JsonDeserialize(using = DateDeserializer.class)
	public Date date;
	public BigDecimal amount;
	public BigDecimal vat;

	public ExpenseDTO() {

	}

	public ExpenseDTO(Long id, String reason, Date date, BigDecimal amount, BigDecimal vat) {
		this.id = id;
		this.reason = reason;
		this.date = date;
		this.amount = amount;
		this.vat = vat;
	}

	@Override
	public String toString() {
		return "ExpenseDTO [id=" + id + ", reason=" + reason + ", date=" + date + ", amount=" + amount + ", vat=" + vat
				+ "]";
	}
}
