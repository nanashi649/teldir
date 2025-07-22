package jp.co.ridi.teldir.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.service.CsvService;
import jp.co.ridi.teldir.service.IndexService;

@Controller
@RequestMapping("/download")
public class DownLoadCsvController {
	@Autowired
	CsvService csvService;
	
	@Autowired
	IndexService service;
	
	@RequestMapping(method = RequestMethod.GET) 
	public void downloadCsv(HttpServletResponse response) throws IOException{
		response.setContentType("text/csv ; charset=UTF-8");
		//Content-Disposition→ファイルの中身の取り扱い方→即時ダウンロード
		response.setHeader("Content-Disposition", "attachment; filename = \"teldata.csv\"");
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(),java.nio.charset.StandardCharsets.UTF_8);
		writer.write('\uFEFF');
		List<TelDataDto> dataList = service.findTelDataList();
		csvService.writeCsv(writer,dataList);
		writer.flush();
		writer.close();
	}
}
