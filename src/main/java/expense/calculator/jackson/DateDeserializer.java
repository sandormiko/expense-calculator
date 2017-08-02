package expense.calculator.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		String date = jp.getText();
		try {
			return DateUtils.parseDate(date,DATE_FORMAT);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

}
