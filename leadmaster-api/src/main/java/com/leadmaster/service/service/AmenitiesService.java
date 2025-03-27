package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.Amenities;
import com.leadmaster.common.dto.AmenitiesDTO;

public interface AmenitiesService {

	public void saveAmenities(AmenitiesDTO amenitiesDTO);

	public List<Amenities> getAllAmenitiess(AmenitiesDTO amenitiesDTO);

	public Amenities getAmenitiesById(AmenitiesDTO amenitiesDTO);

	public void updateAmenities(AmenitiesDTO amenitiesDTO);

	public Amenities deleteAmenities(AmenitiesDTO amenitiesDTO);

}
