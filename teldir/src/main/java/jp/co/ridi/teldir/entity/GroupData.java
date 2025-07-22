package jp.co.ridi.teldir.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tel_group")
public class GroupData {
	@Id
	@GeneratedValue
	private Long groupId;
	

	private String groupName;
	
	private LocalDateTime lastModified;
	
	@OneToMany(mappedBy = "group")
	private List<TelData> userNameList; 
}
