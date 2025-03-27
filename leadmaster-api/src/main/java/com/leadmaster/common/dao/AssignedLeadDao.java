package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.AssignedLead;
import com.leadmaster.common.dto.AssignedLeadDTO;

public interface AssignedLeadDao {

	public AssignedLead saveAssignedLead(AssignedLeadDTO assignedLeadDTO);

	public List<Map<String, Object>> getAllAssignedLeads(AssignedLeadDTO assignedLeadDTO);

	public AssignedLead getAssignedLeadById(Long id);

	public List<String> getLeadIdsByLeadId(Long leadId);

	public void deleteLeadsByLeadId(Long leadId);

	public List<AssignedLead> getAssignedLeadsById(Long id);

	public List<String> existsByLeadIdAndAssignedTo(Long leadId, Long assignedTo);

	public List<Map<String, Object>> getAllLevelThreeLeads(AssignedLeadDTO assignedLeadDTO);

}
