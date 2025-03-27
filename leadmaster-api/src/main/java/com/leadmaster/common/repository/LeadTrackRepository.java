package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadmaster.common.domain.LeadTrack;

@Repository
public interface LeadTrackRepository extends JpaRepository<LeadTrack, Long> {

	@Query("SELECT u FROM LeadTrack u WHERE LOWER(u.leadId) = LOWER(:leadId) AND status = 'Active'")
	List<LeadTrack> getLeadTrackByLeadId(@Param("leadId") Long leadId);

}
