package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.PropertyType;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Long> {

	@Query("SELECT u FROM PropertyType u WHERE LOWER(u.propertyType) = LOWER(:type) AND status = 'Active'")
	List<PropertyType> getPropertyTypeByType(@Param("type") String type);

}
