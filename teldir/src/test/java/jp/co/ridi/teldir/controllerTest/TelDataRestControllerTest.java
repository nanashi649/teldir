package jp.co.ridi.teldir.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.form.TelDataAllForm;
import jp.co.ridi.teldir.form.TelDataForm;
import jp.co.ridi.teldir.service.EditService;

@SpringBootTest
@AutoConfigureMockMvc
public class TelDataRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EditService editService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void saveTelData_shouldReturn200WhenValidForm() throws Exception {
		TelDataForm form = new TelDataForm();
		form.setUserName("山田太郎");
		form.setTelNo("09012345678");
		form.setMailAddr("taro@example.com");

		// JSONに変換
		String json = objectMapper.writeValueAsString(form);

		mockMvc.perform(post("/api/tel/save").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

	@Test
	void shouldReturnBadRequestWhenUserNameIsBlank() throws Exception {
		TelDataForm form = new TelDataForm();
		form.setUserName("");
		form.setTelNo("09012345678");
		form.setMailAddr("taro@example.com");

		// JSONに変換
		String json = objectMapper.writeValueAsString(form);
		mockMvc.perform(post("/api/tel/save").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.userName").value("名前を入力してください"));
	}

	@Test
	void shouldReturnBadRequestWhenMailAddrIsInvalid() throws Exception {
		TelDataForm form = new TelDataForm();
		form.setUserName("山田太郎");
		form.setTelNo("09012345678");
		form.setMailAddr("invalid-email");
		String json = objectMapper.writeValueAsString(form);
		mockMvc.perform(post("/api/tel/save").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.mailAddr").exists());
	}

	@Test
	void shouldReturnAllValidationErrorsWhenMultipleFieldsAreInvalid() throws Exception {
		TelDataForm form = new TelDataForm();
		form.setUserName("");
		form.setTelNo("");
		form.setMailAddr("invalid-email");
		String json = objectMapper.writeValueAsString(form);

		mockMvc.perform(post("/api/tel/save").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.userName").exists())
				.andExpect(jsonPath("$.telNo").exists()).andExpect(jsonPath("$.mailAddr").exists());
	}

	@Test
	void shouldCallSaveTelDataOnce() throws Exception {
		TelDataForm form = new TelDataForm();
		form.setUserName("山田太郎");
		form.setTelNo("09012345678");
		form.setMailAddr("taro@example.com");

		mockMvc.perform(post("/api/tel/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(form))).andExpect(status().isOk());

		verify(editService, times(1)).saveTelData(org.mockito.ArgumentMatchers.any(EditDto.class));
	}

	@Test
	void shouldReturnConflictOnOptimisticLockException() throws Exception {
		// Arrange
		TelDataForm form = new TelDataForm();
		form.setUserName("山田太郎");
		form.setTelNo("09012345678");
		form.setMailAddr("taro@example.com");

		// EditService の saveTelData() 呼び出し時に例外をスローさせる
		doThrow(new OptimisticLockingFailureException("Version mismatch")).when(editService)
				.saveTelData(any(EditDto.class));

		// Act & Assert

		mockMvc.perform(post("/api/tel/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(form))).andExpect(status().isConflict())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.optimisticError").value("Version mismatch"));

		verify(editService, times(1)).saveTelData(any(EditDto.class));

	}

	@Test
	void saveAllTelData_shouldReturn200WhenValidForm() throws Exception {
		TelDataDto dto = new TelDataDto();
		dto.setUserName("山田太郎");
		dto.setTelNo("09012345678");
		dto.setMailAddr("taro@example.com");

		TelDataAllForm form = new TelDataAllForm();
		form.setUsers(List.of(dto));

		String json = objectMapper.writeValueAsString(form);

		mockMvc.perform(post("/api/tel/saveAll").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

	@Test
	void saveAllTelData_shouldReturnBadRequestWhenValidationFails() throws Exception {
		TelDataDto dto = new TelDataDto();
		dto.setUserName(""); // 無効
		dto.setTelNo(""); // 無効
		dto.setMailAddr("invalid-email"); // 無効

		TelDataAllForm form = new TelDataAllForm();
		form.setUsers(List.of(dto));

		String json = objectMapper.writeValueAsString(form);

		mockMvc.perform(post("/api/tel/saveAll").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$['users[0].userName']").exists())
				.andExpect(jsonPath("$['users[0].telNo']").exists())
				.andExpect(jsonPath("$['users[0].mailAddr']").exists());
	}

	@Test
	void saveAllTelData_shouldReturnConflictOnOptimisticLockException() throws Exception {
		TelDataDto dto = new TelDataDto();
		dto.setUserName("山田太郎");
		dto.setTelNo("09012345678");
		dto.setMailAddr("taro@example.com");

		TelDataAllForm form = new TelDataAllForm();
		form.setUsers(List.of(dto));

		doThrow(new OptimisticLockingFailureException("Version mismatch")).when(editService)
				.saveTelData(any(EditDto.class));

		mockMvc.perform(post("/api/tel/saveAll").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(form))).andExpect(status().isConflict())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.optimisticError").value("Version mismatch"));

		verify(editService, times(1)).saveTelData(any(EditDto.class));
	}

}
