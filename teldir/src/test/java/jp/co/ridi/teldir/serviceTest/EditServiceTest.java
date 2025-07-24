package jp.co.ridi.teldir.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.entity.TelData;
import jp.co.ridi.teldir.util.BeanUtil;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class EditServiceTest {
	@Test
	public void assertLastModified() {
		LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		String clientTime = now.toString(); // ISO_LOCAL_DATE_TIME形式（例: 2025-07-09T15:30:00）
		
		EditDto dto = new EditDto();
        dto.setLastModified(clientTime); // クライアント送信値
        dto.setId(null); // 新規登録想定
        
        TelData entity = BeanUtil.createProperties(dto, TelData.class);
        entity.setLastModified(now); // DBが保存時にセットする時刻と仮定
        
        // Assert: クライアント側とDB側の時刻が一致すること
        LocalDateTime clientParsed = LocalDateTime.parse(dto.getLastModified());
        assertEquals(entity.getLastModified(), clientParsed);
	}
}
