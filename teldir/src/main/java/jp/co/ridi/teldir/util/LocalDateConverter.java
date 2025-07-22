package jp.co.ridi.teldir.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

public class LocalDateConverter implements Converter {

    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        if (value == null || StringUtils.isEmpty(value.toString())) {
            return null;
        } else if (value instanceof LocalDate) {
            return (T) value;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            return (T) LocalDate.parse(value.toString(), formatter);
        }
    }
}
