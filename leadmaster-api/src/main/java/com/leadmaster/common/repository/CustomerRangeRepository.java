package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.CustomerRange;

public interface CustomerRangeRepository extends JpaRepository<CustomerRange, Long> {

	@Query("SELECT u FROM CustomerRange u WHERE LOWER(u.customerRange) = LOWER(:range) AND status = 'Active'")
	List<CustomerRange> getCustomerRangeByRange(@Param("range") String range);

}
