package jp.co.ridi.teldir.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jp.co.ridi.teldir.entity.GroupData;
import lombok.Data;

/**
 * 電話帳用フォーム
 */
@Data
public class TelDataForm {
	
	

	// ID
	private Long id;
	
	//groupId
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
	
	//Javascriptには日付型がないためstringで受け取る
	private String lastModified;
	
	// 一覧画面での行選択チェックボックスの値（削除用ID配列）
	private Long[] selectRecords;
	
	private GroupData group;

}
