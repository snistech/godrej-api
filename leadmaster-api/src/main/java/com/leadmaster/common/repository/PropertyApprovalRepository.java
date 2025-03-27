package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.PropertyApproval;

public interface PropertyApprovalRepository extends JpaRepository<PropertyApproval, Long> {

	@Query("SELECT u FROM PropertyApproval u WHERE LOWER(u.approval) = LOWER(:approval) AND status = 'Active'")
	List<PropertyApproval> getPropertyByApproval(@Param("approval") String approval);

}
