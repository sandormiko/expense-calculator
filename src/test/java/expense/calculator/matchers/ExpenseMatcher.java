package expense.calculator.matchers;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;

import java.math.BigDecimal;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;

import expense.calculator.domain.Expense;


public class ExpenseMatcher extends TypeSafeDiagnosingMatcher<Expense> {

	private Matcher<Long> id = new IsAnything<>();

	private Matcher<String> reason = new IsAnything<>();

	private Matcher<BigDecimal> amount = new IsAnything<>();

	private Matcher<BigDecimal> vat = new IsAnything<>();
	private Matcher<Date> date = new IsAnything<>();

	public ExpenseMatcher id(Long id) {
		this.id = is(id);
		return this;
	}

	public ExpenseMatcher reason(String reason) {
		this.reason = is(reason);
		return this;
	}

	public ExpenseMatcher amount(BigDecimal amount) {
		this.amount = is(amount);
		return this;
	}

	public ExpenseMatcher vat(BigDecimal vat) {
		this.vat = is(closeTo(vat, BigDecimal.valueOf(0.1)));
		return this;
	}
	
	public ExpenseMatcher date(Date date) {
		
		this.date = is(date);
		return this;
	}

	@Override
	protected boolean matchesSafely(Expense item, Description mismatchDescription) {
		return matches(id, item.getId(), "id value: ", mismatchDescription)
				&& matches(reason, item.getReason(), "reason value: ", mismatchDescription)
				&& matches(amount, item.getAmount(), "amount value: ", mismatchDescription)
				&& matches(vat, item.getVat(), "vat value: ", mismatchDescription)
				&& matches(date, item.getCreationDate(), "date value: ", mismatchDescription);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(Expense.class.getSimpleName()).appendText(", id: ").appendDescriptionOf(id)
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
