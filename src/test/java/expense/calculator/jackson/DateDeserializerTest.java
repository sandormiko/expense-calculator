package expense.calculator.jackson;

import static expense.calculator.jackson.DateDeserializer.DATE_FORMAT;
import static org.apache.commons.lang3.time.DateFormatUtils.format;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateDeserializerTest {

	private static final String JSON_TEMPLATE = "{\"date\":\"%s\"}";
	
	private DateDeserializer stringToDateDeserialier;
	
	@Before
	public void init() {
		stringToDateDeserialier = new DateDeserializer();
	}

	@Test
	public void deserializeDateString() {
		try {
			String dateToParse = "11/11/2017";
			String json = String.format(JSON_TEMPLATE, dateToParse);
			InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
			ObjectMapper mapper = new ObjectMapper();
			JsonParser parser = mapper.getFactory().createParser(stream);
			DeserializationContext ctxt = mapper.getDeserializationContext();

			// Date text value is the last
			// START_OBJECT, FIELD_NAME, VALUE_STRING
			parser.nextTextValue();
			parser.nextTextValue();
			parser.nextTextValue();

			Date date = stringToDateDeserialier.deserialize(parser, ctxt);
			assertThat(format(date, DATE_FORMAT), equalTo(dateToParse));
		} catch (Exception e) {
			fail(e.getMessage());

		}
	}

	@Test(expected = RuntimeException.class)
	public void failOnDeserialzingInvalidDateString() throws JsonParseException, IOException {

		String dateToParse = "apple";
		String json = String.format(JSON_TEMPLATE, dateToParse);
		InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser = mapper.getFactory().createParser(stream);
		DeserializationContext ctxt = mapper.getDeserializationContext();

		parser.nextTextValue();
		parser.nextTextValue();
		parser.nextTextValue();

		stringToDateDeserialier.deserialize(parser, ctxt);

	}
}
