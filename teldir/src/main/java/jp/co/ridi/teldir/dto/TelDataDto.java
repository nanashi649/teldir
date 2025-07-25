package jp.co.ridi.teldir.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import jp.co.ridi.teldir.entity.TelGroup;
import jp.co.ridi.teldir.form.telNoformatter.PhoneNumberFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TelDataDto {

	// ID
	private Long id;

	// ユーザー名
	@NotBlank(message = "名前を入力してください")
	private String userName;

	// 電話番号
	@NotBlank(message = "電話番号を入力してください")

	private String telNo;

	private String lastModified;

	// メールアドレス
	@NotBlank(message = "メールアドレスを入力してください")
	@Email
	private String mailAddr;

	private TelGroup group;

	private transient int rowIndex;

	public TelDataDto(Long id, String userName, String telNo, String mailAddr, TelGroup group) {
		this.id = id;
		this.userName = userName;
		this.telNo = telNo;
		this.mailAddr = mailAddr;
		this.group = group;
	}

	private List<PhoneNumberFormatter> formatters;

	/**
	 * @return
	 */
	public String formatPhoneNumber() {
		if (telNo == null) {
			return null;
		}
		String formattedNubmerOnly = telNo.replaceAll("[^0-9]", ""); // 数字以外を全て除去

		for (PhoneNumberFormatter formatter : formatters) {
			if (formatter.supports(formattedNubmerOnly)) {
				return formatter.format(formattedNubmerOnly);
			}
		}

		return telNo;
	}
}
