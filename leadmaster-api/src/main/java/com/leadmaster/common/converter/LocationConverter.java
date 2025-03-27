package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Location;
import com.leadmaster.common.dto.LocationDTO;

@Component
public class LocationConverter {

	public static Location getLocationByLocationDTO(LocationDTO locationDTO) {
		Location location = new Location();
		location.setId(locationDTO.getId());
		location.setLocation(locationDTO.getLocation());
		location.setSubLocation(locationDTO.getSubLocation());
		location.setStatus(locationDTO.getStatus());
		location.setCreatedDate(locationDTO.getCreatedDate());
		location.setCreatedBy(locationDTO.getCreatedBy());
		location.setUpdatedDate(locationDTO.getUpdatedDate());
		location.setUpdatedBy(locationDTO.getUpdatedBy());

		return location;
	}

	public static LocationDTO getLocationDTOByLocation(Location location) {
		LocationDTO dto = new LocationDTO();

		dto.setId(location.getId());
		dto.setLocation(location.getLocation());
		dto.setSubLocation(location.getSubLocation());
		dto.setStatus(location.getStatus());
		dto.setCreatedDate(location.getCreatedDate());
		dto.setCreatedBy(location.getCreatedBy());
		dto.setUpdatedDate(location.getUpdatedDate());
		dto.setUpdatedBy(location.getUpdatedBy());

		return dto;
	}

}
