package jp.co.ridi.teldir.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class TelGroup {
	@Id
	@GeneratedValue
	private Long groupId;

	private String groupName;

	private LocalDateTime lastModified;

	@OneToMany(mappedBy = "group")
	private List<TelData> userNameList;
}
