package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.BudgetRange;

public interface BudgetRangeRepository extends JpaRepository<BudgetRange, Long> {

	@Query("SELECT u FROM BudgetRange u WHERE LOWER(u.budgetRange) = LOWER(:range) AND status = 'Active'")
	List<BudgetRange> getBudgetRangeByRange(@Param("range") String range);

}
