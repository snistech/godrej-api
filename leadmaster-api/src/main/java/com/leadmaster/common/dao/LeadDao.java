package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.Lead;
import com.leadmaster.common.dto.LeadDTO;

public interface LeadDao {

	public Lead saveLead(LeadDTO leadsDTO);

	public List<Map<String, Object>> getAllLeads(LeadDTO leadsDTO);

	public Lead getLeadById(Long id);

	public List<Lead> getLeadByPhone(String phoneNumber);

	public List<Lead> getAllLead(LeadDTO leadsDTO);

	public List<Map<String, Object>> getAllLeadsCount(LeadDTO leadsDTO);

}