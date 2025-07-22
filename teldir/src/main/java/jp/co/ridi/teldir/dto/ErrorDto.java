package jp.co.ridi.teldir.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class ErrorDto {

    private List<ErrorDetailDto> errors = new ArrayList<>();

    public void addError(String message) {
        addError(StringUtils.EMPTY, message);

    }

    public void addError(String field, String message) {
        ErrorDetailDto d = new ErrorDetailDto();
        d.setField(field);
        d.setMessage(message);
        errors.add(d);
    }

}
