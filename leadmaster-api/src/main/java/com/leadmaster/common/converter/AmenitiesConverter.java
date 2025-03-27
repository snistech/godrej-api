package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Amenities;
import com.leadmaster.common.dto.AmenitiesDTO;

@Component
public class AmenitiesConverter {

	public static Amenities getAmenitiesByAmenitiesDTO(AmenitiesDTO amenitiesDTO) {
		Amenities amenities = new Amenities();
		amenities.setId(amenitiesDTO.getId());
		amenities.setAmenity(amenitiesDTO.getAmenity());
		amenities.setStatus(amenitiesDTO.getStatus());
		amenities.setCreatedDate(amenitiesDTO.getCreatedDate());
		amenities.setCreatedBy(amenitiesDTO.getCreatedBy());
		amenities.setUpdatedDate(amenitiesDTO.getUpdatedDate());
		amenities.setUpdatedBy(amenitiesDTO.getUpdatedBy());

		return amenities;
	}

	public static AmenitiesDTO getAmenitiesDTOByAmenities(Amenities amenities) {
		AmenitiesDTO dto = new AmenitiesDTO();

		dto.setId(amenities.getId());
		dto.setAmenity(amenities.getAmenity());
		dto.setStatus(amenities.getStatus());
		dto.setCreatedDate(amenities.getCreatedDate());
		dto.setCreatedBy(amenities.getCreatedBy());
		dto.setUpdatedDate(amenities.getUpdatedDate());
		dto.setUpdatedBy(amenities.getUpdatedBy());

		return dto;
	}

}
