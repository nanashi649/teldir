package jp.co.ridi.teldir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ridi.teldir.entity.TelGroup;
import jp.co.ridi.teldir.repository.GroupDataRepository;

@Service
public class IndexGroupService {
	@Autowired
	GroupDataRepository repository;

	/**
	 * @return
	 */
	public List<TelGroup> findGroupDataList() {
		return repository.findAll();
	}

	/**
	 * @param idArray
	 */
	public void deleteGroupData(Long... idArray) {
		if (idArray == null) {
			return;
		}
		for (long id : idArray) {
			repository.deleteById(id);
		}
	}
}
