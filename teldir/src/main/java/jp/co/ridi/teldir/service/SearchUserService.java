package jp.co.ridi.teldir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ridi.teldir.entity.TelData;
import jp.co.ridi.teldir.repository.TelDataRepository;

@Service
public class SearchUserService {

	@Autowired
	private TelDataRepository telDataRepository;

	public List<TelData> searchByUserNameAndGroupName(String userName, String groupName) {
		List<TelData> result = telDataRepository.searchByUserNameAndGroupName(userName, groupName);
		return result;
	}
}
