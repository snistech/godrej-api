package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.PropertyValues;
import com.leadmaster.common.dto.PropertyValuesDTO;

@Component
public class PropertyValuesConverter {

	public static PropertyValues getPropertyValueByPropertyValueDTO(PropertyValuesDTO propertyValuesDTO) {
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.setId(propertyValuesDTO.getId());
		propertyValues.setCategory(propertyValuesDTO.getCategory());
		propertyValues.setType(propertyValuesDTO.getType());
		propertyValues.setProjectName(propertyValuesDTO.getProjectName());
		propertyValues.setLocation(propertyValuesDTO.getLocation());
		propertyValues.setSubLocation(propertyValuesDTO.getSubLocation());
		propertyValues.setApartmentType(propertyValuesDTO.getApartmentType());
		propertyValues.setLookingFor(propertyValuesDTO.getLookingFor());
		propertyValues.setBudgetFrom(propertyValuesDTO.getBudgetFrom());
		propertyValues.setBudgetTo(propertyValuesDTO.getBudgetTo());
		propertyValues.setPossession(propertyValuesDTO.getPossession());
		propertyValues.setPropertyLink(propertyValuesDTO.getPropertyLink());
		propertyValues.setStatus(propertyValuesDTO.getStatus());
		propertyValues.setCreatedDate(propertyValuesDTO.getCreatedDate());
		propertyValues.setCreatedBy(propertyValuesDTO.getCreatedBy());
		propertyValues.setUpdatedDate(propertyValuesDTO.getUpdatedDate());
		propertyValues.setUpdatedBy(propertyValuesDTO.getUpdatedBy());

		return propertyValues;
	}

	public static PropertyValuesDTO getPropertyValueDTOByPropertyValue(PropertyValues propertyValues) {
		PropertyValuesDTO dto = new PropertyValuesDTO();

		dto.setId(propertyValues.getId());
		dto.setCategory(propertyValues.getCategory());
		dto.setType(propertyValues.getType());
		dto.setProjectName(propertyValues.getProjectName());
		dto.setLocation(propertyValues.getLocation());
		dto.setSubLocation(propertyValues.getSubLocation());
		dto.setApartmentType(propertyValues.getApartmentType());
		dto.setLookingFor(propertyValues.getLookingFor());
		dto.setBudgetFrom(propertyValues.getBudgetFrom());
		dto.setBudgetTo(propertyValues.getBudgetTo());
		dto.setPossession(propertyValues.getPossession());
		dto.setPropertyLink(propertyValues.getPropertyLink());
		dto.setStatus(propertyValues.getStatus());
		dto.setCreatedDate(propertyValues.getCreatedDate());
		dto.setCreatedBy(propertyValues.getCreatedBy());
		dto.setUpdatedDate(propertyValues.getUpdatedDate());
		dto.setUpdatedBy(propertyValues.getUpdatedBy());

		return dto;
	}

}
