package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.PropertyValues;
import com.leadmaster.common.dto.PropertyValuesDTO;

public interface PropertyValuesService {

	public void savePropertyValue(PropertyValuesDTO propertyValuesDTO);

	public List<PropertyValues> getAllPropertyValues(PropertyValuesDTO propertyValuesDTO);

	public PropertyValues getPropertyValueById(PropertyValuesDTO propertyValuesDTO);

	public void updatePropertyValue(PropertyValuesDTO propertyValuesDTO);

	public PropertyValues deletePropertyValue(PropertyValuesDTO propertyValuesDTO);

	public List<PropertyValues> getPropertyValueData(PropertyValuesDTO propertyValuesDTO);

	public List<Map<String, Object>> getPropertyValues(PropertyValuesDTO propertyValuesDTO);
}
