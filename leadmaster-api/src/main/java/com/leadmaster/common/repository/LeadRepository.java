package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadmaster.common.domain.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

	@Query("SELECT u FROM Lead u WHERE u.phoneNumber = :phoneNumber AND status = 'Active' ORDER BY u.id DESC")
	List<Lead> getLeadByPhone(@Param("phoneNumber") String phoneNumber);

}