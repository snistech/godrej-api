package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.PropertyType;
import com.leadmaster.common.dto.PropertyTypeDTO;

public interface PropertyTypeService {

	public void savePropertyType(PropertyTypeDTO propertyTypeDTO);

	public List<PropertyType> getAllPropertyTypes(PropertyTypeDTO propertyTypeDTO);

	public PropertyType getPropertyTypeById(PropertyTypeDTO propertyTypeDTO);

	public void updatePropertyType(PropertyTypeDTO propertyTypeDTO);

	public PropertyType deletePropertyType(PropertyTypeDTO propertyTypeDTO);

}
