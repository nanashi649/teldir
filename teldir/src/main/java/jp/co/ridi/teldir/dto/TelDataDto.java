package jp.co.ridi.teldir.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jp.co.ridi.teldir.entity.GroupData;
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

	private GroupData group;

	private transient int rowIndex;
	
	
	

	public TelDataDto(Long id, String userName, String telNo, String mailAddr, GroupData group) {
		this.id = id;
		this.userName = userName;
		this.telNo = telNo;
		this.mailAddr = mailAddr;
		this.group = group;
	}



}
