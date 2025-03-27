package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.PropertyType;
import com.leadmaster.common.dto.PropertyTypeDTO;

public interface PropertyTypeDao {

	public PropertyType savePropertyType(PropertyTypeDTO propertyTypeDTO);

	public List<PropertyType> getAllPropertyType(PropertyTypeDTO propertyTypeDTO);

	public PropertyType getPropertyTypeById(Long id);

	public List<PropertyType> getPropertyTypeByType(String type);

}
