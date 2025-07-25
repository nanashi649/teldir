package jp.co.ridi.teldir.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.dto.EditGroupDto;
import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.form.TelDataAllForm;
import jp.co.ridi.teldir.form.TelDataForm;
import jp.co.ridi.teldir.form.TelGroupForm;
import jp.co.ridi.teldir.service.EditGroupService;
import jp.co.ridi.teldir.service.EditService;
import jp.co.ridi.teldir.util.BeanUtil;

@Controller
@RequestMapping("/api/tel")
public class TelDataRestController {
	@Autowired
	EditService service;

	@Autowired
	EditGroupService groupService;

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@PostMapping("/save")
	public ResponseEntity<?> saveTelData(@RequestBody @Valid TelDataForm form, BindingResult result) {
		// ヴァリデーションエラーがあった場合.badRequest()を返す
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : result.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			// 保存処理
			service.saveTelData(BeanUtil.createProperties(form, EditDto.class));
			return ResponseEntity.ok().build();// 成功
		} catch (OptimisticLockingFailureException e) {
			return handleOptimisticLockError(e);
		} catch (Exception e) {
			return handleServerError(e, null);
		}
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@PostMapping("/saveGroup")
	public ResponseEntity<?> saveGroupTelData(@RequestBody @Valid TelGroupForm form, BindingResult result) {
		// ヴァリデーションエラーがあった場合.badRequest()を返す
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : result.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			// 保存処理
			groupService.saveGroupData(BeanUtil.createProperties(form, EditGroupDto.class));
			return ResponseEntity.ok().build();// 成功
		} catch (OptimisticLockingFailureException e) {
			return handleOptimisticLockError(e);
		} catch (Exception e) {
			return handleServerError(e, null);
		}
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@PostMapping("/saveAll")
	public ResponseEntity<?> saveAllTelData(@RequestBody @Valid TelDataAllForm form, BindingResult result) {
		// ヴァリデーションエラーがあった場合.badRequest()を返す
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : result.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			// 保存処理
			for (TelDataDto dto : form.getUsers()) {
				service.saveTelData(BeanUtil.createProperties(dto, EditDto.class));
			}
			return ResponseEntity.ok().build();// 成功
		} catch (OptimisticLockingFailureException e) {
			return handleOptimisticLockError(e);
		} catch (Exception e) {
			return handleServerError(e, null);
		}
	}

	/**
	 * @param e
	 * @param status
	 * @return
	 */
	private ResponseEntity<Map<String, String>> handleServerError(Exception e, HttpStatus status) {
		Map<String, String> error = new HashMap<>();
		error.put("message", e.getMessage());
		return ResponseEntity.status(status).body(error);
	}

	/**
	 * @param e
	 * @return
	 */
	public ResponseEntity<Map<String, String>> handleOptimisticLockError(OptimisticLockingFailureException e) {
		Map<String, String> error = new HashMap<>();
		error.put("optimisticError", e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

}