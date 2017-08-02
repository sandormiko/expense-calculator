package expense.calculator.matchers;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;

import java.math.BigDecimal;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;

import expense.calculator.domain.ExpenseDTO;

public class ExpenseDTOMatcher extends TypeSafeDiagnosingMatcher<ExpenseDTO> {

	private Matcher<Long> id = new IsAnything<>();

	private Matcher<String> reason = new IsAnything<>();

	private Matcher<BigDecimal> amount = new IsAnything<>();

	private Matcher<BigDecimal> vat = new IsAnything<>();
	private Matcher<Date> date = new IsAnything<>();

	public ExpenseDTOMatcher id(Long id) {
		this.id = is(id);
		return this;
	}

	public ExpenseDTOMatcher reason(String reason) {
		this.reason = is(reason);
		return this;
	}

	public ExpenseDTOMatcher amount(BigDecimal amount) {
		this.amount = is(amount);
		return this;
	}

	public ExpenseDTOMatcher vat(BigDecimal vat) {
		this.vat = is(closeTo(vat, BigDecimal.valueOf(0.1)));
		return this;
	}
	
	public ExpenseDTOMatcher date(Date date) {
		
		this.date = is(date);
		return this;
	}

	@Override
	protected boolean matchesSafely(ExpenseDTO item, Description mismatchDescription) {
		return matches(id, item.id, "id value: ", mismatchDescription)
				&& matches(reason, item.reason, "reason value: ", mismatchDescription)
				&& matches(amount, item.amount, "amount value: ", mismatchDescription)
				&& matches(vat, item.vat, "vat value: ", mismatchDescription)
				&& matches(date, item.date, "date value: ", mismatchDescription);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(ExpenseDTO.class.getSimpleName()).appendText(", id: ").appendDescriptionOf(id)
				.appendText(", reason: ").appendDescriptionOf(reason).appendText(", amount: ")
				.appendDescriptionOf(amount).appendText(", vat: ").appendDescriptionOf(vat)
				.appendText(", date: ").appendDescriptionOf(date);
	}

	protected <X> boolean matches(Matcher<? extends X> matcher, X value, String attribute,
			Description mismatchDescription) {
		if (!matcher.matches(value)) {
			mismatchDescription.appendText(" " + attribute + " ");
			matcher.describeMismatch(value, mismatchDescription);
			return false;
		} else {
			return true;
		}
	}

}
