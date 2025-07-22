package jp.co.ridi.teldir.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class TelDataAllDto {
	@Valid
	//一括処理用list
	private List<TelDataDto> users = new ArrayList<>();
	
}
