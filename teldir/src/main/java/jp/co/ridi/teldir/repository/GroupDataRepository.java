package jp.co.ridi.teldir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ridi.teldir.entity.GroupData;

@Repository
public interface GroupDataRepository extends JpaRepository<GroupData, Long> {
}
