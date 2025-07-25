package jp.co.ridi.teldir.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.service.CsvService;
import jp.co.ridi.teldir.service.EditService;
import jp.co.ridi.teldir.util.BeanUtil;

@Controller
@RequestMapping("/upload")
public class UploadCsvController {

	@Autowired
	CsvService csvService;

	@Autowired
	EditService service;

	/**
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String uploadCsv(@RequestParam("file") MultipartFile file, org.springframework.ui.Model model) {
		if (file.isEmpty()) {
			model.addAttribute("message", "ファイルが選択されていません");
			return "uploadResult";
		}
		try {
			List<TelDataDto> teldataList = csvService.parseCsv(file);
			for (TelDataDto dto : teldataList) {
				service.saveTelData(BeanUtil.createProperties(dto, EditDto.class));
			}
			model.addAttribute("message", "CSVアップロード成功");
		} catch (Exception e) {
			model.addAttribute("message", "CSVアップロード失敗: " + e.getMessage());
		}
		return "uploadResult";
	}
}
