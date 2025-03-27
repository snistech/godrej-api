package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.ApartmentType;
import com.leadmaster.common.dto.ApartmentTypeDTO;

@Component
public class ApartmentTypeConverter {

	public static ApartmentType getApartmentTypeByApartmentTypeDTO(ApartmentTypeDTO apartmentTypeDTO) {
		ApartmentType apartmentType = new ApartmentType();
		apartmentType.setId(apartmentTypeDTO.getId());
		apartmentType.setType(apartmentTypeDTO.getType());
		apartmentType.setStatus(apartmentTypeDTO.getStatus());
		apartmentType.setCreatedDate(apartmentTypeDTO.getCreatedDate());
		apartmentType.setCreatedBy(apartmentTypeDTO.getCreatedBy());
		apartmentType.setUpdatedDate(apartmentTypeDTO.getUpdatedDate());
		apartmentType.setUpdatedBy(apartmentTypeDTO.getUpdatedBy());

		return apartmentType;
	}

	public static ApartmentTypeDTO getApartmentTypeDTOByApartmentType(ApartmentType apartmentType) {
		ApartmentTypeDTO dto = new ApartmentTypeDTO();

		dto.setId(apartmentType.getId());
		dto.setType(apartmentType.getType());
		dto.setStatus(apartmentType.getStatus());
		dto.setCreatedDate(apartmentType.getCreatedDate());
		dto.setCreatedBy(apartmentType.getCreatedBy());
		dto.setUpdatedDate(apartmentType.getUpdatedDate());
		dto.setUpdatedBy(apartmentType.getUpdatedBy());

		return dto;
	}

}
