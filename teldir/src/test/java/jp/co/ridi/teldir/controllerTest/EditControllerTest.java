package jp.co.ridi.teldir.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.form.TelDataAllForm;
import jp.co.ridi.teldir.util.BeanUtil;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class EditControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void idTest() throws Exception {
		mockMvc.perform(get("/edit/all").param("id", "1")).andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("name=\"users[0].id\"")));
	}

//	@Test
//	public void saveTest() throws Exception {
//		Map<String, String> params = new HashMap<>();
//		params.put("lastModified", "2025-07-09T10:00:00");
////		params.put("users[0].id", "1");
//		params.put("users[0].userName", "テスト太郎");
//		params.put("users[0].telNo", "09012345678");
//		params.put("users[0].mailAddr", "test@example.com");
//
//		mockMvc.perform(post("/edit/all/saveAll").param("lastModified", params.get("lastModified"))
////				.param("users[0].id", params.get("users[0].id"))
//				.param("users[0].userName", params.get("users[0].userName"))
//				.param("users[0].telNo", params.get("users[0].telNo"))
//				.param("users[0].mailAddr", params.get("users[0].mailAddr"))).andExpect(status().is3xxRedirection())
//				.andExpect(redirectedUrl("/")); // 成功時はTOPにリダイレクト
//	}



	@Test
	public void setTimeForm() {
		TelDataAllForm form = new TelDataAllForm();

		LocalDateTime time = LocalDateTime.of(2025, 7, 9, 15, 30);

		form.setUsers(List.of(new TelDataDto()));

		String formatted = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		form.getUsers().get(0).setLastModified(formatted);

		assertEquals("2025-07-09T15:30:00", form.getUsers().get(0).getLastModified());
	}

	@Test
	public void lastModifiedFormBind() throws Exception {
		String lastModified = "2025-07-09T15:30:00";

		mockMvc.perform(post("/edit/all/saveAll").param("name", "山田太郎").param("telNo", "090-1234-5678")
				.param("lastModified", lastModified)).andExpect(status().is3xxRedirection()) // 成功後リダイレクトされる想定
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void TelDataDtoToEditDto() {
		TelDataDto telDataDto = new TelDataDto();
		telDataDto.setLastModified("2025-07-09T15:30:00");

		EditDto editDto = BeanUtil.createProperties(telDataDto, EditDto.class);

		// Assert
		assertEquals("2025-07-09T15:30:00", editDto.getLastModified());
	}
}
