package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	@Query("SELECT u FROM Location u WHERE LOWER(u.subLocation) = LOWER(:location) AND status = 'Active'")
	List<Location> getLocationByName(@Param("location") String location);

}
