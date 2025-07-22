package jp.co.ridi.teldir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ridi.teldir.entity.TelData;

@Repository
public interface TelDataRepository extends JpaRepository<TelData, Long> {
	
	@Query("SELECT t FROM TelData t WHERE " + 
			"(:userName IS NULL OR t.userName LIKE %:userName%) AND" +
			"(:groupName IS NULL OR t.group.groupName LIKE %:groupName%)")
	 List<TelData> searchByUserNameAndGroupName(
		        @Param("userName") String userName,
		        @Param("groupName") String groupName
		        );
}
