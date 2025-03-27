package com.leadmaster.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadmaster.common.domain.VisitedLeads;

@Repository
public interface VisitedLeadsRepository extends JpaRepository<VisitedLeads, Long> {

}
