package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadmaster.common.domain.AssignedLead;

@Repository
public interface AssignedLeadRepository extends JpaRepository<AssignedLead, Long> {

	@Query("SELECT u.leadId FROM AssignedLead u WHERE u.leadId = :leadId AND u.status = 'Active'")
	List<String> getLeadIdsByLeadId(@Param("leadId") Long leadId);

	@Modifying
	@Query("delete from AssignedLead u where u.leadId=:leadId")
	void deleteLeadsByLeadId(@Param("leadId") Long leadId);

	@Query("SELECT u FROM AssignedLead u WHERE LOWER(u.leadId) = LOWER(:leadId) AND status = 'Active'")
	List<AssignedLead> getAssignedLeadsById(@Param("leadId") Long leadId);

	@Query("SELECT u.id FROM AssignedLead u WHERE LOWER(u.leadId) = LOWER(:leadId) AND LOWER(u.assignedTo) = LOWER(:assignedTo) AND u.status = 'Active'")
	List<String> existsByLeadIdAndAssignedTo(@Param("leadId") Long leadId, @Param("assignedTo") Long assignedTo);

}
