package jp.co.ridi.teldir.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jp.co.ridi.teldir.dto.EditGroupDto;
import jp.co.ridi.teldir.dto.GroupDataDto;
import jp.co.ridi.teldir.entity.GroupData;
import jp.co.ridi.teldir.entity.TelData;
import jp.co.ridi.teldir.repository.GroupDataRepository;
import jp.co.ridi.teldir.util.BeanUtil;

@Service
public class EditGroupService {
	@Autowired
	private GroupDataRepository groupDataRepository;

	public GroupDataDto findGroupData(Long groupId) {

		// optionalがあるとcreatePropertiesが使えないのでgroupDataに一度変換
		Optional<GroupData> optional = groupDataRepository.findById(groupId);

		GroupData entity = optional.get();
		LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		entity.setLastModified(now);
		groupDataRepository.save(entity);
		return BeanUtil.createProperties(entity, GroupDataDto.class);
	}

	public void saveGroupData(EditGroupDto dto) {
		// idがnullの時（新規作成の時）
		if (dto.getGroupId() == null) {
			// --- 新規登録処理 ---
			GroupData entity = BeanUtil.createProperties(dto, GroupData.class);

			groupDataRepository.save(entity);
			return; // ★更新処理へ進まないようにする
		}
//		Optional<GroupData> optional = groupDataRepository.findById(dto.getGroupId());
//		GroupData existing = optional.get();
		// 取得した時間の秒未満切り捨て
		LocalDateTime dbTime = groupDataRepository.findById(dto.getGroupId())
				.get().getLastModified().truncatedTo(ChronoUnit.SECONDS);

		// クライアントから渡されたlastmodified(文字列)を日時に変換
		LocalDateTime clientModified = LocalDateTime.parse(dto.getLastModified());

		// クライアントから渡されたlastmodified(文字列)とDB上のlastmodifiedが合わなかったらエラーを飛ばす
		if (!dbTime.isEqual(clientModified)) {
			throw new OptimisticLockingFailureException("ほかのユーザーによって更新されています。\n再度読み込んでください。");
		}

		GroupData entity = BeanUtil.createProperties(dto, GroupData.class);

		entity.setLastModified(clientModified);

		groupDataRepository.save(entity);
	}
}
