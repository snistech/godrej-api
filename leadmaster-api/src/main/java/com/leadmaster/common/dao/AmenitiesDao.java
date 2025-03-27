package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.Amenities;
import com.leadmaster.common.dto.AmenitiesDTO;

public interface AmenitiesDao {

	public Amenities saveAmenities(AmenitiesDTO amenitiesDTO);

	public List<Amenities> getAllAmenities(AmenitiesDTO amenitiesDTO);

	public Amenities getAmenitiesById(Long id);

	public List<Amenities> getAmenitiesByAmenity(String amenity);

}
