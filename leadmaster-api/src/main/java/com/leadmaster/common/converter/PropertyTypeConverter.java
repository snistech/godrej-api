package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.PropertyType;
import com.leadmaster.common.dto.PropertyTypeDTO;

@Component
public class PropertyTypeConverter {

	public static PropertyType getPropertyTypeByPropertyTypeDTO(PropertyTypeDTO propertyTypeDTO) {
		PropertyType propertyType = new PropertyType();
		propertyType.setId(propertyTypeDTO.getId());
		propertyType.setPropertyType(propertyTypeDTO.getPropertyType());
		propertyType.setStatus(propertyTypeDTO.getStatus());
		propertyType.setCreatedDate(propertyTypeDTO.getCreatedDate());
		propertyType.setCreatedBy(propertyTypeDTO.getCreatedBy());
		propertyType.setUpdatedDate(propertyTypeDTO.getUpdatedDate());
		propertyType.setUpdatedBy(propertyTypeDTO.getUpdatedBy());

		return propertyType;
	}

	public static PropertyTypeDTO getPropertyTypeDTOByPropertyType(PropertyType propertyType) {
		PropertyTypeDTO dto = new PropertyTypeDTO();

		dto.setId(propertyType.getId());
		dto.setPropertyType(propertyType.getPropertyType());
		dto.setStatus(propertyType.getStatus());
		dto.setCreatedDate(propertyType.getCreatedDate());
		dto.setCreatedBy(propertyType.getCreatedBy());
		dto.setUpdatedDate(propertyType.getUpdatedDate());
		dto.setUpdatedBy(propertyType.getUpdatedBy());

		return dto;
	}

}
