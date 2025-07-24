package jp.co.ridi.teldir.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import jp.co.ridi.teldir.dto.TelDataDto;

@Service
public class CsvService {
	public void writeCsv(Writer writer, List<TelDataDto> dataList) throws IOException {
		for (TelDataDto data : dataList) {
			writer.write(String.join(",", addSpaceAfterComma(data.getUserName()), addSpaceAfterComma(data.getTelNo()),
					addSpaceAfterComma(data.getMailAddr())));
			writer.write("\n");
		}
	}

	// ,のあとにスペース
	/**
	 * @param s
	 * @return
	 */
	private String addSpaceAfterComma(String s) {
		return s != null ? s.replaceAll(",", ", ") : "";
	}

	// MultipartFileで
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public List<TelDataDto> parseCsv(MultipartFile file) throws IOException, CsvValidationException {
		List<TelDataDto> result = new ArrayList<>();
		try (
				// MultipartFile から バイト単位の入力ストリーム を取得→バイトストリームを UTF-8エンコーディングに基づいて「文字列」に変換
				// →行単位の読み取り
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
				// 読み取った行をcsvで解析
				CSVReader csvReader = new CSVReader(reader);) {
			String[] line;

			// csvReader.readNext()をlineに代入
			while ((line = csvReader.readNext()) != null) {
				TelDataDto dto = new TelDataDto();
				// csvの1列目（インデックス0)
				dto.setUserName(line[0]);
				dto.setTelNo(line[1]);
				dto.setMailAddr(line[2]);
				result.add(dto);

			}
		}
		return result;
	}
}
