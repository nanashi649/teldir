package jp.co.ridi.teldir.dto;

import java.util.List;

import jp.co.ridi.teldir.entity.TelGroup;
import jp.co.ridi.teldir.entity.TelData;
import lombok.Data;

@Data
public class SearchResultDto {
	private List<TelGroup> groupData;
	private List<TelData> telData;
}
