package jp.co.ridi.teldir.form.telNoformatter;

public interface PhoneNumberFormatter {
	boolean supports(String number);

	String format(String number);
}
