package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.ApartmentType;

public interface ApartmentTypeRepository extends JpaRepository<ApartmentType, Long> {

	@Query("SELECT u FROM ApartmentType u WHERE LOWER(u.type) = LOWER(:type) AND status = 'Active'")
	List<ApartmentType> getApartmentTypeByType(@Param("type") String type);

}
