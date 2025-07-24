package jp.co.ridi.teldir.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.form.TelDataAllForm;
import jp.co.ridi.teldir.form.TelDataForm;
import jp.co.ridi.teldir.service.EditService;
import jp.co.ridi.teldir.util.BeanUtil;

@Controller
@RequestMapping("/edit")
public class EditController {

	@Autowired
	private EditService service;

	@RequestMapping(method = RequestMethod.GET)
	String index(@ModelAttribute TelDataForm form, Model model) {
//		 新規作成モード
		if (form.getId() == null) {
			return "edit";
		}

		TelDataDto dto = service.findTelData(form.getId());
		TelDataForm telDataForm = BeanUtil.createProperties(dto, TelDataForm.class);

		// グループ情報がある場合,グループデータをformにセットする
		if (dto.getGroup() != null) {
			telDataForm.setGroupId(dto.getGroup().getGroupId());
		}
		// 編集モード
		model.addAttribute("telDataForm", telDataForm);
		return "edit";
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String editAll(@ModelAttribute TelDataAllForm form, Model model) {

		// 必ず1件だけ初期化する（null・空を防ぐ）
		form.setUsers(List.of(new TelDataDto()));

		return "editAll";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute @Validated TelDataForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "edit";
		}

		service.saveTelData(BeanUtil.createProperties(form, EditDto.class));

		return "redirect:/";
	}

	// 一括保存用
	@RequestMapping(value = "/all/saveAll", method = RequestMethod.POST)
	public String saveAll(@ModelAttribute @Validated TelDataAllForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			System.out.println("バリデーションエラー:");
			result.getAllErrors().forEach(error -> {
				System.out.println(error.getObjectName() + " -> " + error.getDefaultMessage());
			});
			return "editAll";
		}
		for (TelDataDto dto : form.getUsers()) {
			service.saveTelData(BeanUtil.createProperties(dto, EditDto.class));
		}

		return "redirect:/";
	}

}
