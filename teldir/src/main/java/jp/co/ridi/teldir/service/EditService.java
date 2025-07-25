package jp.co.ridi.teldir.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jp.co.ridi.teldir.dto.EditDto;
import jp.co.ridi.teldir.dto.TelDataDto;
import jp.co.ridi.teldir.entity.TelData;
import jp.co.ridi.teldir.entity.TelGroup;
import jp.co.ridi.teldir.repository.GroupDataRepository;
import jp.co.ridi.teldir.repository.TelDataRepository;
import jp.co.ridi.teldir.util.BeanUtil;

@Service
public class EditService {

	@Autowired
	private TelDataRepository telDataRepository;

	@Autowired
	private GroupDataRepository groupDataRepository;

	/**
	 * @param id
	 * @return
	 */
	public TelDataDto findTelData(final Long id) {
		TelData telData = telDataRepository.getOne(id);
		// findTelDataを実行したとき現在時間を取得し、DBに保存
		LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		telData.setLastModified(now);
		telDataRepository.save(telData);
		// dtoに変換してserviceクラスに受け渡し
		TelDataDto dto = BeanUtil.createProperties(telData, TelDataDto.class);
		return dto;
	}

	/**
	 * @param dto
	 */
	public void saveTelData(EditDto dto) {
		// idがnullの時（新規作成の時）
		if (dto.getId() == null) {
			// --- 新規登録処理 ---
			TelData entity = BeanUtil.createProperties(dto, TelData.class);

			telDataRepository.save(entity);
			return; // ★更新処理へ進まないようにする
		}
		// 排他エラー処理
		// dbの現在時刻を情報を取得
		Optional<TelData> optional = telDataRepository.findById(dto.getId());

		TelData existing = optional.get();

		// 取得した時間の秒未満切り捨て
		LocalDateTime dbTime = existing.getLastModified().truncatedTo(ChronoUnit.SECONDS);

		// クライアントから渡されたlastmodified(文字列)を日時に変換
		LocalDateTime clientModified = LocalDateTime.parse(dto.getLastModified());

		// クライアントから渡されたlastmodified(文字列)とDB上のlastmodifiedが合わなかったらエラーを飛ばす
		if (!dbTime.isEqual(clientModified)) {
			throw new OptimisticLockingFailureException("ほかのユーザーによって更新されています。\n再度読み込んでください。");
		}

		// 受け取ったdtoをentityに変換
		TelData entity = BeanUtil.createProperties(dto, TelData.class);

		// entityは日時型でlastmodified(文字列)と合わないので個別にsetする
		entity.setLastModified(clientModified);

		// groupIdがある場合,groupDataを取得してセット(編集画面でgroupIdを保持するため）
		if (dto.getGroupId() != null) {
			TelGroup group = groupDataRepository.findById(dto.getGroupId()).orElse(null);
			entity.setGroup(group);
		}
		// enitiyをDBに保存
		telDataRepository.save(entity);
	}

	/**
	 * @param ids
	 * @return
	 */
	public List<TelData> findTelAllData(List<Long> ids) {
		return telDataRepository.findAllById(ids);
	}

}
