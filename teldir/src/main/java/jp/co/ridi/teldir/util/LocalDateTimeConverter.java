package jp.co.ridi.teldir.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class LocalDateTimeConverter implements Converter {

    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        if (value == null) {
            //nullで返却
            return null;
        } else if (value instanceof LocalDateTime) {
            //LocalDateTimeで返却
            return (T) value;
        } else if (value instanceof Date) {
            //Date → LocalDateTimeに変換して返却
            Date date = (Date)value;
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            return (T) LocalDateTime.ofInstant(instant, zone);
        } else {
            //上記以外は全てnullで返却
            return null;
        }
    }
}
