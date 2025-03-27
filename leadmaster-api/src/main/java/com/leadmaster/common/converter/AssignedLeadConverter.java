package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.AssignedLead;
import com.leadmaster.common.dto.AssignedLeadDTO;

@Component
public class AssignedLeadConverter {

	public static AssignedLead getAssignedLeadByAssignedLeadDTO(AssignedLeadDTO assignedLeadDTO) {
		AssignedLead assignedLead = new AssignedLead();
		assignedLead.setId(assignedLeadDTO.getId());
		assignedLead.setLeadId(assignedLeadDTO.getLeadId());
		assignedLead.setFullName(assignedLeadDTO.getFullName());
		assignedLead.setPhoneNumber(assignedLeadDTO.getPhoneNumber());
		assignedLead.setAlternativeNumber(assignedLeadDTO.getAlternativeNumber());
		assignedLead.setLocation(assignedLeadDTO.getLocation());
		assignedLead.setSubLocation(assignedLeadDTO.getSubLocation());
		assignedLead.setLookingFor(assignedLeadDTO.getLookingFor());
		assignedLead.setTenantType(assignedLeadDTO.getTenantType());
		assignedLead.setApartmentType(assignedLeadDTO.getApartmentType());
		assignedLead.setAmenities(assignedLeadDTO.getAmenities());
		assignedLead.setPropertyApproval(assignedLeadDTO.getPropertyApproval());
		assignedLead.setPlotSize(assignedLeadDTO.getPlotSize());
		assignedLead.setSuggestedPlot(assignedLeadDTO.getSuggestedPlot());
		assignedLead.setBudgetRange(assignedLeadDTO.getBudgetRange());
		assignedLead.setCustomerOccupation(assignedLeadDTO.getCustomerOccupation());
		assignedLead.setIncomeRange(assignedLeadDTO.getIncomeRange());
		assignedLead.setPossession(assignedLeadDTO.getPossession());
		assignedLead.setRemarks(assignedLeadDTO.getRemarks());
		assignedLead.setLeadStatus(assignedLeadDTO.getLeadStatus());
		assignedLead.setAssignedTo(assignedLeadDTO.getAssignedTo());
		assignedLead.setAssignedBy(assignedLeadDTO.getAssignedBy());
		assignedLead.setDataCollector(assignedLeadDTO.getDataCollector());
		assignedLead.setVisitingDate(assignedLeadDTO.getVisitingDate());
		assignedLead.setVisitedDate(assignedLeadDTO.getVisitedDate());
		assignedLead.setConversionDate(assignedLeadDTO.getConversionDate());
		assignedLead.setFinalizedCounsellor(assignedLeadDTO.getFinalizedCounsellor());
		assignedLead.setFinalizedProperty(assignedLeadDTO.getFinalizedProperty());
		assignedLead.setFinalizedProof(assignedLeadDTO.getFinalizedProof());
		assignedLead.setFinalizedStatus(assignedLeadDTO.getFinalizedStatus());
		assignedLead.setLeadSource(assignedLeadDTO.getLeadSource());
		assignedLead.setLeadFlag(assignedLeadDTO.getLeadFlag());
		assignedLead.setStatus(assignedLeadDTO.getStatus());
		assignedLead.setCreatedDate(assignedLeadDTO.getCreatedDate());
		assignedLead.setCreatedBy(assignedLeadDTO.getCreatedBy());
		assignedLead.setUpdatedDate(assignedLeadDTO.getUpdatedDate());
		assignedLead.setUpdatedBy(assignedLeadDTO.getUpdatedBy());

		return assignedLead;
	}

	public static AssignedLeadDTO getAssignedLeadDTOByAssignedLead(AssignedLead assignedLead) {
		AssignedLeadDTO dto = new AssignedLeadDTO();

		dto.setId(assignedLead.getId());
		dto.setLeadId(assignedLead.getLeadId());
		dto.setFullName(assignedLead.getFullName());
		dto.setPhoneNumber(assignedLead.getPhoneNumber());
		dto.setLocation(assignedLead.getLocation());
		dto.setSubLocation(assignedLead.getSubLocation());
		dto.setLookingFor(assignedLead.getLookingFor());
		dto.setTenantType(assignedLead.getTenantType());
		dto.setApartmentType(assignedLead.getApartmentType());
		dto.setAmenities(assignedLead.getAmenities());
		dto.setPropertyApproval(assignedLead.getPropertyApproval());
		dto.setPlotSize(assignedLead.getPlotSize());
		dto.setSuggestedPlot(assignedLead.getSuggestedPlot());
		dto.setBudgetRange(assignedLead.getBudgetRange());
		dto.setCustomerOccupation(assignedLead.getCustomerOccupation());
		dto.setIncomeRange(assignedLead.getIncomeRange());
		dto.setPossession(assignedLead.getPossession());
		dto.setRemarks(assignedLead.getRemarks());
		dto.setLeadStatus(assignedLead.getLeadStatus());
		dto.setAssignedTo(assignedLead.getAssignedTo());
		dto.setAssignedBy(assignedLead.getAssignedBy());
		dto.setDataCollector(assignedLead.getDataCollector());
		dto.setVisitingDate(assignedLead.getVisitingDate());
		dto.setVisitedDate(assignedLead.getVisitedDate());
		dto.setConversionDate(assignedLead.getConversionDate());
		dto.setFinalizedCounsellor(assignedLead.getFinalizedCounsellor());
		dto.setFinalizedProperty(assignedLead.getFinalizedProperty());
		dto.setFinalizedProof(assignedLead.getFinalizedProof());
		dto.setFinalizedStatus(assignedLead.getFinalizedStatus());
		dto.setLeadSource(assignedLead.getLeadSource());
		dto.setLeadFlag(assignedLead.getLeadFlag());
		dto.setStatus(assignedLead.getStatus());
		dto.setCreatedDate(assignedLead.getCreatedDate());
		dto.setCreatedBy(assignedLead.getCreatedBy());
		dto.setUpdatedDate(assignedLead.getUpdatedDate());
		dto.setUpdatedBy(assignedLead.getUpdatedBy());

		return dto;
	}

}