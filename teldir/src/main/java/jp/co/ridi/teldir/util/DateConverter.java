package jp.co.ridi.teldir.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {

    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        if (value == null) {
            //nullで返却
            return null;
        } else if (value instanceof Date) {
            //Dateで返却
            return (T) value;
        } else if (value instanceof LocalDateTime) {
            //LocalDateTime → Dateに変換して返却
            LocalDateTime datetime = (LocalDateTime)value;
            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = ZonedDateTime.of(datetime, zone);
            Instant instant = zonedDateTime.toInstant();
            return (T) Date.from(instant);
        } else {
            //上記以外は全てnullで返却
            return null;
        }
    }
}
