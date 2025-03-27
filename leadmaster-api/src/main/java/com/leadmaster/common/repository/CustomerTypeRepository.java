package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.CustomerType;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {

	@Query("SELECT u FROM CustomerType u WHERE LOWER(u.type) = LOWER(:type) AND status = 'Active'")
	List<CustomerType> getCustomerTypeByType(@Param("type") String type);

}
