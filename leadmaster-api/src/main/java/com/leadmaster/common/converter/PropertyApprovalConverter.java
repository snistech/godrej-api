package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.PropertyApproval;
import com.leadmaster.common.dto.PropertyApprovalDTO;

@Component
public class PropertyApprovalConverter {

	public static PropertyApproval getPropertyApprovalByPropertyApprovalDTO(PropertyApprovalDTO propertyApprovalDTO) {
		PropertyApproval propertyApproval = new PropertyApproval();
		propertyApproval.setId(propertyApprovalDTO.getId());
		propertyApproval.setApproval(propertyApprovalDTO.getApproval());
		propertyApproval.setStatus(propertyApprovalDTO.getStatus());
		propertyApproval.setCreatedDate(propertyApprovalDTO.getCreatedDate());
		propertyApproval.setCreatedBy(propertyApprovalDTO.getCreatedBy());
		propertyApproval.setUpdatedDate(propertyApprovalDTO.getUpdatedDate());
		propertyApproval.setUpdatedBy(propertyApprovalDTO.getUpdatedBy());

		return propertyApproval;
	}

	public static PropertyApprovalDTO getPropertyApprovalDTOByPropertyApproval(PropertyApproval propertyApproval) {
		PropertyApprovalDTO dto = new PropertyApprovalDTO();

		dto.setId(propertyApproval.getId());
		dto.setApproval(propertyApproval.getApproval());
		dto.setStatus(propertyApproval.getStatus());
		dto.setCreatedDate(propertyApproval.getCreatedDate());
		dto.setCreatedBy(propertyApproval.getCreatedBy());
		dto.setUpdatedDate(propertyApproval.getUpdatedDate());
		dto.setUpdatedBy(propertyApproval.getUpdatedBy());

		return dto;
	}

}
