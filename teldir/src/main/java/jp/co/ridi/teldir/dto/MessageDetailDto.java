package jp.co.ridi.teldir.dto;

import jp.co.ridi.teldir.constant.Message;
import lombok.Data;

@Data
public class MessageDetailDto {

    private Message messageKey;

    private String[] params;

}
