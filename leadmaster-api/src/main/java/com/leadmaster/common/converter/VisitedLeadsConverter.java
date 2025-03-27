package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.VisitedLeads;
import com.leadmaster.common.dto.VisitedLeadsDTO;

@Component
public class VisitedLeadsConverter {

	public static VisitedLeads getVisitedLeadsByVisitedLeadsDTO(VisitedLeadsDTO visitedLeadsDTO) {
		VisitedLeads visitedLeads = new VisitedLeads();
		visitedLeads.setId(visitedLeadsDTO.getId());
		visitedLeads.setLeadId(visitedLeadsDTO.getLeadId());
		visitedLeads.setProperty(visitedLeadsDTO.getProperty());
		visitedLeads.setVisitedDate(visitedLeadsDTO.getVisitedDate());
		visitedLeads.setPropertyVisitCounsellor(visitedLeadsDTO.getPropertyVisitCounsellor());
		visitedLeads.setRemarks(visitedLeadsDTO.getRemarks());
		visitedLeads.setStatus(visitedLeadsDTO.getStatus());
		visitedLeads.setCreatedDate(visitedLeadsDTO.getCreatedDate());
		visitedLeads.setCreatedBy(visitedLeadsDTO.getCreatedBy());
		visitedLeads.setUpdatedDate(visitedLeadsDTO.getUpdatedDate());
		visitedLeads.setUpdatedBy(visitedLeadsDTO.getUpdatedBy());

		return visitedLeads;
	}

	public static VisitedLeadsDTO getVisitedLeadsDTOByVisitedLeads(VisitedLeads visitedLeads) {
		VisitedLeadsDTO dto = new VisitedLeadsDTO();

		dto.setId(visitedLeads.getId());
		dto.setLeadId(visitedLeads.getLeadId());
		dto.setProperty(visitedLeads.getProperty());
		dto.setVisitedDate(visitedLeads.getVisitedDate());
		dto.setPropertyVisitCounsellor(visitedLeads.getPropertyVisitCounsellor());
		dto.setRemarks(visitedLeads.getRemarks());
		dto.setStatus(visitedLeads.getStatus());
		dto.setCreatedDate(visitedLeads.getCreatedDate());
		dto.setCreatedBy(visitedLeads.getCreatedBy());
		dto.setUpdatedDate(visitedLeads.getUpdatedDate());
		dto.setUpdatedBy(visitedLeads.getUpdatedBy());

		return dto;
	}

}
