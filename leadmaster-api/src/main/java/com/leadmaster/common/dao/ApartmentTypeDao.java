package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.ApartmentType;
import com.leadmaster.common.dto.ApartmentTypeDTO;

public interface ApartmentTypeDao {

	public ApartmentType saveApartmentType(ApartmentTypeDTO apartmentTypeDTO);

	public List<ApartmentType> getAllApartmentType(ApartmentTypeDTO apartmentTypeDTO);

	public ApartmentType getApartmentTypeById(Long id);

	public List<ApartmentType> getApartmentTypeByType(String type);

}
