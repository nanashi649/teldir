package jp.co.ridi.teldir.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GroupDataForm {
	private Long groupId;
	
	@NotBlank(message = "グループ名を入力してください。")
	private String groupName;
	
	private String lastModified;
	// 一覧画面での行選択チェックボックスの値（削除用ID配列）
	private Long[] selectRecords;

}
