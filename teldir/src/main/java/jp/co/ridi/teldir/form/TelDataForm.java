package jp.co.ridi.teldir.form;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import jp.co.ridi.teldir.entity.TelGroup;
import jp.co.ridi.teldir.form.telNoformatter.PhoneNumberFormatter;
import lombok.Data;

/**
 * 電話帳用フォーム
 */
@Data
public class TelDataForm {

	// ID
	private Long id;

	// groupId
	private Long groupId;

	// ユーザー名
	@NotBlank(message = "名前を入力してください")
	private String userName;

	// 電話番号
	@NotBlank(message = "電話番号を入力してください")
	private String telNo;

	// メールアドレス
	@NotBlank(message = "メールアドレスを入力してください")
	@Email
	private String mailAddr;

	// Javascriptには日付型がないためstringで受け取る
	private String lastModified;

	// 一覧画面での行選択チェックボックスの値（削除用ID配列）
	private Long[] selectRecords;

	private TelGroup group;

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
