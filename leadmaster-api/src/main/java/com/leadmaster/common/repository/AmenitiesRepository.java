package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.Amenities;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {

	@Query("SELECT u FROM Amenities u WHERE LOWER(u.amenity) = LOWER(:amenity) AND status = 'Active'")
	List<Amenities> getAmenitiesByAmenity(@Param("amenity") String amenity);

}
