package jp.co.ridi.teldir.formTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.ridi.teldir.form.TelDataForm;
import jp.co.ridi.teldir.form.telNoformatter.PhoneNumberFormatter;

@SpringBootTest
public class TelDataFormTest {
	@Autowired
	private List<PhoneNumberFormatter> formatters;

	@Test
	void shouldFormatMobilePhoneNumber() {
		TelDataForm data = new TelDataForm();
		data.setFormatters(formatters);
		data.setTelNo("09012345678");
		assertEquals("090-1234-5678", data.formatPhoneNumber());
	}

	@Test
	void shouldFormatOsakaFixedLine() {
		TelDataForm data = new TelDataForm();
		data.setFormatters(formatters);
		data.setTelNo("0612345678");
		assertEquals("06-1234-5678", data.formatPhoneNumber());
	}

	@Test
	void shouldNomalNumber() {
		TelDataForm data = new TelDataForm();
		data.setFormatters(formatters);
		data.setTelNo("0822452111");
		assertEquals("0822452111", data.formatPhoneNumber());
	}
}
