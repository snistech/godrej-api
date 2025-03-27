package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.AssignedLead;
import com.leadmaster.common.domain.Lead;
import com.leadmaster.common.dto.AssignedLeadDTO;
import com.leadmaster.common.dto.LeadDTO;
import com.leadmaster.common.dto.VisitedLeadsDTO;

public interface LeadService {

	// leads
	public void saveLead(LeadDTO leadDTO);

	public List<Map<String, Object>> getAllLeads(LeadDTO leadDTO);

	public Lead getLeadById(LeadDTO leadDTO);

	public void updateLead(LeadDTO leadDTO);

	public void assignLeads(LeadDTO leadDTO);

	public void updateAssignLead(LeadDTO leadDTO);

	public List<Map<String, Object>> getAllLeadsCount(LeadDTO leadDTO);

	// visited leads saving
	public void saveVisitedLead(VisitedLeadsDTO visitedLeadsDTO);

	public List<Map<String, Object>> getVisitedLeads(VisitedLeadsDTO visitedLeadsDTO);

	// third version api
	public void saveAssignedLead(AssignedLeadDTO assignedLeadDTO);

	public void saveNewAssignedLead(AssignedLeadDTO assignedLeadDTO);

	public List<Map<String, Object>> getAllAssignedLeads(AssignedLeadDTO assignedLeadDTO);

	public AssignedLead getAssignedLeadById(AssignedLeadDTO assignedLeadDTO);

	public void updateAssignedLead(AssignedLeadDTO assignedLeadDTO);

	public List<Map<String, Object>> getAllLevelThreeLeads(AssignedLeadDTO assignedLeadDTO);

}