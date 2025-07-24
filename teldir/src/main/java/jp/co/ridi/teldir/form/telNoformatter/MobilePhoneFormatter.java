package jp.co.ridi.teldir.form.telNoformatter;

import org.springframework.stereotype.Component;

@Component
public class MobilePhoneFormatter implements PhoneNumberFormatter {
	@Override
	public boolean supports(String number) {
		return number != null && number.length() == 11;
	}

	@Override
	public String format(String number) {
		return number.substring(0, 3) + "-" + number.substring(3, 7) + "-" + number.substring(7);
	}
}
