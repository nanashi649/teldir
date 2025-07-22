package jp.co.ridi.teldir.dto;

import lombok.Data;

@Data
public class EditDto {
	private Long id;
	private String userName;
	private String telNo;
	private String mailAddr;
	private String lastModified;

	// トップ画面でgroupIdを保持するためのプロパティ
	private Long groupId;
}
