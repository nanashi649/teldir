package jp.co.ridi.teldir.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import jp.co.ridi.teldir.dto.TelDataDto;
import lombok.Data;

@Data
public class TelDataAllForm {
	@Valid
	//一括処理用list
	private List<TelDataDto> users = new ArrayList<>();
	
}
