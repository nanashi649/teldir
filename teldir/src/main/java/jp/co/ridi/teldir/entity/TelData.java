package jp.co.ridi.teldir.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

@Data
@Entity
public class TelData {

	@Id
	@GeneratedValue
	private Long id;

	private String userName;

	private String telNo;

	private String mailAddr;

	private LocalDateTime lastModified;

	@ManyToOne
	@JoinColumn(name = "group_id")
	@NotFound(action = NotFoundAction.IGNORE) // groupidが見つからないときは無視する
	private GroupData group;

}
