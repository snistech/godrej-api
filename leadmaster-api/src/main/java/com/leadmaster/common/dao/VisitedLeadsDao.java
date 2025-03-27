package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.VisitedLeads;
import com.leadmaster.common.dto.VisitedLeadsDTO;

public interface VisitedLeadsDao {

	public VisitedLeads saveVisitedLead(VisitedLeadsDTO visitedLeadsDTO);

//	public VisitedLeads getVisitedLeadById(Long id);
//
	public List<Map<String, Object>> getVisitedLeads(VisitedLeadsDTO visitedLeadsDTO);

}
