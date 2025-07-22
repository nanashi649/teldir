//package jp.co.ridi.teldir.Service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import jp.co.ridi.teldir.dto.TelDataAllDto;
//import jp.co.ridi.teldir.dto.TelDataDto;
//import jp.co.ridi.teldir.service.EditAllService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
//public class EditAllServiceTest {
//	@Autowired
//	private EditAllService service;
//
//	@Test
//	public void GetTime() {
//		TelDataAllDto dto = new TelDataAllDto();
//
//		dto.getUsers().add(new TelDataDto());
//
//		String before = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);;
//
//		String result = service.getTime(dto);
//
//		String after = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);;
//
//		assertFalse(result.compareTo(before) < 0); // result >= before
//		assertFalse(result.compareTo(after) > 0); // result <= after
//
//		// dtoにもセットされているか確認
//		assertEquals(result, dto.getUsers().get(0).getLastModified());
//	}
//}
