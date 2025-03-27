package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.ApartmentType;
import com.leadmaster.common.dto.ApartmentTypeDTO;

public interface ApartmentTypeService {

	public void saveApartmentType(ApartmentTypeDTO apartmentTypeDTO);

	public List<ApartmentType> getAllApartmentTypes(ApartmentTypeDTO apartmentTypeDTO);

	public ApartmentType getApartmentTypeById(ApartmentTypeDTO apartmentTypeDTO);

	public void updateApartmentType(ApartmentTypeDTO apartmentTypeDTO);

	public ApartmentType deleteApartmentType(ApartmentTypeDTO apartmentTypeDTO);

}
