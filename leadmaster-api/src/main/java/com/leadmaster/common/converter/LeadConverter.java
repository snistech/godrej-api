package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Lead;
import com.leadmaster.common.dto.LeadDTO;

@Component
public class LeadConverter {

	public static Lead getLeadByLeadDTO(LeadDTO leadDTO) {
		Lead lead = new Lead();
		lead.setId(leadDTO.getId());
		lead.setFullName(leadDTO.getFullName());
		lead.setPhoneNumber(leadDTO.getPhoneNumber());
		lead.setAlternativeNumber(leadDTO.getAlternativeNumber());
		lead.setLocation(leadDTO.getLocation());
		lead.setSubLocation(leadDTO.getSubLocation());
		lead.setLookingFor(leadDTO.getLookingFor());
		lead.setTenantType(leadDTO.getTenantType());
		lead.setApartmentType(leadDTO.getApartmentType());
		lead.setAmenities(leadDTO.getAmenities());
		lead.setPropertyApproval(leadDTO.getPropertyApproval());
		lead.setPlotSize(leadDTO.getPlotSize());
		lead.setSuggestedPlot(leadDTO.getSuggestedPlot());
		lead.setBudgetRange(leadDTO.getBudgetRange());
		lead.setCustomerOccupation(leadDTO.getCustomerOccupation());
		lead.setIncomeRange(leadDTO.getIncomeRange());
		lead.setPossession(leadDTO.getPossession());
		lead.setRemarks(leadDTO.getRemarks());
		lead.setLeadStatus(leadDTO.getLeadStatus());
		lead.setAssignedTo(leadDTO.getAssignedTo());
		lead.setAssignedBy(leadDTO.getAssignedBy());
		lead.setDataCollector(leadDTO.getDataCollector());
		lead.setVisitingDate(leadDTO.getVisitingDate());
		lead.setVisitedDate(leadDTO.getVisitedDate());
		lead.setConversionDate(leadDTO.getConversionDate());
		lead.setFinalizedCounsellor(leadDTO.getFinalizedCounsellor());
		lead.setFinalizedProperty(leadDTO.getFinalizedProperty());
		lead.setFinalizedProof(leadDTO.getFinalizedProof());
		lead.setFinalizedStatus(leadDTO.getFinalizedStatus());
		lead.setLeadSource(leadDTO.getLeadSource());
		lead.setLeadFlag(leadDTO.getLeadFlag());
		lead.setStatus(leadDTO.getStatus());
		lead.setCreatedDate(leadDTO.getCreatedDate());
		lead.setCreatedBy(leadDTO.getCreatedBy());
		lead.setUpdatedDate(leadDTO.getUpdatedDate());
		lead.setUpdatedBy(leadDTO.getUpdatedBy());

		return lead;
	}

	public static LeadDTO getLeadDTOByLead(Lead lead) {
		LeadDTO dto = new LeadDTO();

		dto.setId(lead.getId());
		dto.setFullName(lead.getFullName());
		dto.setPhoneNumber(lead.getPhoneNumber());
		dto.setAlternativeNumber(lead.getAlternativeNumber());
		dto.setLocation(lead.getLocation());
		dto.setSubLocation(lead.getSubLocation());
		dto.setLookingFor(lead.getLookingFor());
		dto.setTenantType(lead.getTenantType());
		dto.setApartmentType(lead.getApartmentType());
		dto.setAmenities(lead.getAmenities());
		dto.setPropertyApproval(lead.getPropertyApproval());
		dto.setPlotSize(lead.getPlotSize());
		dto.setSuggestedPlot(lead.getSuggestedPlot());
		dto.setBudgetRange(lead.getBudgetRange());
		dto.setCustomerOccupation(lead.getCustomerOccupation());
		dto.setIncomeRange(lead.getIncomeRange());
		dto.setPossession(lead.getPossession());
		dto.setRemarks(lead.getRemarks());
		dto.setLeadStatus(lead.getLeadStatus());
		dto.setAssignedTo(lead.getAssignedTo());
		dto.setAssignedBy(lead.getAssignedBy());
		dto.setDataCollector(lead.getDataCollector());
		dto.setVisitingDate(lead.getVisitingDate());
		dto.setVisitedDate(lead.getVisitedDate());
		dto.setConversionDate(lead.getConversionDate());
		dto.setFinalizedCounsellor(lead.getFinalizedCounsellor());
		dto.setFinalizedProperty(lead.getFinalizedProperty());
		dto.setFinalizedProof(lead.getFinalizedProof());
		dto.setFinalizedStatus(lead.getFinalizedStatus());
		dto.setLeadSource(lead.getLeadSource());
		dto.setLeadFlag(lead.getLeadFlag());
		dto.setStatus(lead.getStatus());
		dto.setCreatedDate(lead.getCreatedDate());
		dto.setCreatedBy(lead.getCreatedBy());
		dto.setUpdatedDate(lead.getUpdatedDate());
		dto.setUpdatedBy(lead.getUpdatedBy());

		return dto;
	}

}