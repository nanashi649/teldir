package jp.co.ridi.teldir.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import jp.co.ridi.teldir.constant.Message;

@Component
public class MessageUtil {

    private static MessageSource sMessageSource;

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    public void init() {
        sMessageSource = messageSource;
    }

    public static String getMessage(Message message, String[] params) {
        return getMessage(message, (Object[]) params);
    }

    public static String getMessage(Message message, Object... params) {
        return sMessageSource.getMessage(message.getKey(), params, LocaleContextHolder.getLocale());
    }

    public static String getMessage(MessageSourceResolvable resolvable) {
        return sMessageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }

}
