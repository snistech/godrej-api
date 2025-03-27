package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.PropertyValues;
import com.leadmaster.common.dto.PropertyValuesDTO;

public interface PropertyValuesDao {

	public PropertyValues savePropertyValue(PropertyValuesDTO propertyValuesDTO);

	public List<PropertyValues> getAllPropertyValue(PropertyValuesDTO propertyValuesDTO);

	public PropertyValues getPropertyValueById(Long id);

	public List<PropertyValues> getPropertyValueData(PropertyValuesDTO propertyValuesDTO);

	public List<Map<String, Object>> getPropertyValues(PropertyValuesDTO propertyValuesDTO);

}
