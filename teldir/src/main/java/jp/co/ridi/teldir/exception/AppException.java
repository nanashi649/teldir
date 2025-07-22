package jp.co.ridi.teldir.exception;

import jp.co.ridi.teldir.constant.Message;
import jp.co.ridi.teldir.dto.MessageDto;
import lombok.Getter;
import lombok.Setter;

public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private MessageDto messageDto;

    public AppException(MessageDto dto) {
        this.messageDto = dto;
    }

    public AppException(Message key, String... params) {
        this.messageDto = new MessageDto(key, params);
    }

}
