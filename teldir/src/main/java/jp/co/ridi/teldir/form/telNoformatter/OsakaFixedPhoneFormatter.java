package jp.co.ridi.teldir.form.telNoformatter;

import org.springframework.stereotype.Component;

@Component
public class OsakaFixedPhoneFormatter implements PhoneNumberFormatter {
	@Override
	public boolean supports(String number) {
		return number != null && number.length() == 10 && number.startsWith("06");
	}

	@Override
	public String format(String number) {
		return number.substring(0, 2) + "-" + number.substring(2, 6) + "-" + number.substring(6);
	}

}
