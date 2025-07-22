package jp.co.ridi.teldir.dto;

import java.util.ArrayList;
import java.util.List;

import jp.co.ridi.teldir.constant.Message;
import lombok.Data;

@Data
public class MessageDto {

    private List<MessageDetailDto> messages = new ArrayList<>();

    public MessageDto() {

    }

    public MessageDto(Message key, String... params) {
        addMessage(key, params);
    }

    public void addMessage(Message key, String... params) {
        MessageDetailDto d = new MessageDetailDto();
        d.setMessageKey(key);
        d.setParams(params);
        messages.add(d);
    }

}
