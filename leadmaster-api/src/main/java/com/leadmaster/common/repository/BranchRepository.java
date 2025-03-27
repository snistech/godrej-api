package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

	@Query("SELECT u FROM Branch u WHERE LOWER(u.branch) = LOWER(:branch) AND status = 'Active'")
	List<Branch> getBranchByName(@Param("branch") String branchName);

}
