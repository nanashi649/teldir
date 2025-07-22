package jp.co.ridi.teldir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.ridi.teldir.dto.EditGroupDto;
import jp.co.ridi.teldir.dto.GroupDataDto;
import jp.co.ridi.teldir.form.GroupDataForm;
import jp.co.ridi.teldir.service.EditGroupService;
import jp.co.ridi.teldir.util.BeanUtil;

@Controller
@RequestMapping("/editGroup")
public class EditGroupController {
	@Autowired
	EditGroupService service;

	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute GroupDataForm form, Model model) {
		if (form.getGroupId() == null) {
			// 新規作成モード（IDが渡ってきていない）
			return "editGroup";
		}
//		System.out.println("groupId = " + form.getGroupId()); // ここが null ならバインド失敗
//		GroupDataDto groupDto = service.findGroupData(form.getGroupId());
//		GroupDataForm groupDataForm = BeanUtil.createProperties(service.findGroupData(form.getGroupId()),GroupDataForm.class);
		model.addAttribute("groupDataForm", 
				BeanUtil.createProperties(service.findGroupData(form.getGroupId()), GroupDataForm.class));

		return "editGroup";
	}

//	@RequestMapping(method = RequestMethod.GET)
//	public String index(@RequestParam(name = "groupId", required = false) Long groupId, Model model) {
//		if (groupId == null) {
//			System.out.println("groupId が null です");
//			return "editGroup";
//		}
//
//		GroupDataForm form =  service.findGroupData(groupId);
//		model.addAttribute("groupDataForm", form);
//
//		return "editGroup";
//	}

	@RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
	public String save(@ModelAttribute @Validated GroupDataForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "editGroup";
		}
		service.saveGroupData(BeanUtil.createProperties(form, EditGroupDto.class));
		return "redirect:/";
	}

}
