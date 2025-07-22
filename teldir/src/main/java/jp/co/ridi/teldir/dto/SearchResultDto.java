package jp.co.ridi.teldir.dto;

import java.util.List;

import jp.co.ridi.teldir.entity.GroupData;
import jp.co.ridi.teldir.entity.TelData;
import lombok.Data;

@Data
public class SearchResultDto {
	private List<GroupData> groupData;
	private List<TelData> telData;
}
