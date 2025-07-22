package jp.co.ridi.teldir.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jp.co.ridi.teldir.entity.TelData;

@DataJpaTest
public class TelDataRepositoryTest {
	@Autowired
	private TelDataRepository telDataRepository;

	@Test
	public void saveAutoId() {
		TelData telData = new TelData();
		telData.setUserName("テスト太郎");
		telData.setTelNo("090-1234-5678");
		telData.setMailAddr("test@example.com");
		
		// Act: 保存
        TelData saved = telDataRepository.save(telData);
        
        assertThat(saved.getId()).isNotNull();
        System.out.println("自動採番されたID: " + saved.getId());
	}
}
