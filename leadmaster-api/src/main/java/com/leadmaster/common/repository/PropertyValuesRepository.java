package com.leadmaster.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadmaster.common.domain.PropertyValues;

public interface PropertyValuesRepository extends JpaRepository<PropertyValues, Long> {

}
