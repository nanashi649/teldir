package jp.co.ridi.teldir.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jp.co.ridi.teldir.dto.EditGroupDto;
import jp.co.ridi.teldir.dto.TelGroupDto;
import jp.co.ridi.teldir.entity.TelGroup;
import jp.co.ridi.teldir.repository.GroupDataRepository;
import jp.co.ridi.teldir.util.BeanUtil;

@Service
public class EditGroupService {
	@Autowired
	private GroupDataRepository groupDataRepository;

	/**
	 * @param groupId
	 * @return
	 */
	public TelGroupDto findGroupData(Long groupId) {

		// optionalがあるとcreatePropertiesが使えないのでgroupDataに一度変換
		Optional<TelGroup> optional = groupDataRepository.findById(groupId);

		TelGroup entity = optional.get();
		LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		entity.setLastModified(now);
		groupDataRepository.save(entity);
		return BeanUtil.createProperties(entity, TelGroupDto.class);
	}

	/**
	 * @param dto
	 */
	public void saveGroupData(EditGroupDto dto) {
		// idがnullの時（新規作成の時）
		if (dto.getGroupId() == null) {
			// --- 新規登録処理 ---
			TelGroup entity = BeanUtil.createProperties(dto, TelGroup.class);

			groupDataRepository.save(entity);
			return; // ★更新処理へ進まないようにする
		}

		// 取得した時間の秒未満切り捨て
		LocalDateTime dbTime = groupDataRepository.findById(dto.getGroupId()).get().getLastModified()
				.truncatedTo(ChronoUnit.SECONDS);

		// クライアントから渡されたlastmodified(文字列)を日時に変換
		LocalDateTime clientModified = LocalDateTime.parse(dto.getLastModified());

		// クライアントから渡されたlastmodified(文字列)とDB上のlastmodifiedが合わなかったらエラーを飛ばす
		if (!dbTime.isEqual(clientModified)) {
			throw new OptimisticLockingFailureException("ほかのユーザーによって更新されています。\n再度読み込んでください。");
		}

		TelGroup entity = BeanUtil.createProperties(dto, TelGroup.class);

		entity.setLastModified(clientModified);

		groupDataRepository.save(entity);
	}
}
