package com.leadmaster.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.PropertyValuesConverter;
import com.leadmaster.common.dao.PropertyValuesDao;
import com.leadmaster.common.domain.PropertyValues;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.PropertyValuesDTO;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.PropertyValuesService;

@Service("PropertyValuesServiceImpl")
public class PropertyValuesServiceImpl implements PropertyValuesService {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertyValuesServiceImpl.class);

	@Resource(name = "PropertyValuesDaoImpl")
	PropertyValuesDao propertyValuesDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void savePropertyValue(PropertyValuesDTO propertyValuesDTO) {
		List<Role> roles = loginService.getAllUserRoles(propertyValuesDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save PropertyValues Details.");

		propertyValuesDao.savePropertyValue(propertyValuesDTO);
		LOGGER.info("PropertyValues added successfully by " + propertyValuesDTO.getCreatedBy());
	}

	@Override
	public List<PropertyValues> getAllPropertyValues(PropertyValuesDTO propertyValuesDTO) {
		return propertyValuesDao.getAllPropertyValue(propertyValuesDTO);
	}

	@Override
	public PropertyValues getPropertyValueById(PropertyValuesDTO propertyValuesDTO) {
		return propertyValuesDao.getPropertyValueById(propertyValuesDTO.getId());
	}

	@Override
	public void updatePropertyValue(PropertyValuesDTO propertyValuesDTO) {
		List<Role> roles = loginService.getAllUserRoles(propertyValuesDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update PropertyValues Details.");

		PropertyValues propertyValues = propertyValuesDao.getPropertyValueById(propertyValuesDTO.getId());
		PropertyValuesDTO dbPropertyValueDTO = PropertyValuesConverter
				.getPropertyValueDTOByPropertyValue(propertyValues);

		if (null != propertyValuesDTO.getCategory())
			dbPropertyValueDTO.setCategory(propertyValuesDTO.getCategory());

		if (null != propertyValuesDTO.getType())
			dbPropertyValueDTO.setType(propertyValuesDTO.getType());

		if (null != propertyValuesDTO.getProjectName())
			dbPropertyValueDTO.setProjectName(propertyValuesDTO.getProjectName());

		if (null != propertyValuesDTO.getLocation())
			dbPropertyValueDTO.setLocation(propertyValuesDTO.getLocation());

		if (null != propertyValuesDTO.getSubLocation())
			dbPropertyValueDTO.setSubLocation(propertyValuesDTO.getSubLocation());

		if (null != propertyValuesDTO.getApartmentType())
			dbPropertyValueDTO.setApartmentType(propertyValuesDTO.getApartmentType());

		if (null != propertyValuesDTO.getLookingFor())
			dbPropertyValueDTO.setLookingFor(propertyValuesDTO.getLookingFor());

		if (null != propertyValuesDTO.getBudgetFrom())
			dbPropertyValueDTO.setBudgetFrom(propertyValuesDTO.getBudgetFrom());

		if (null != propertyValuesDTO.getBudgetTo())
			dbPropertyValueDTO.setBudgetTo(propertyValuesDTO.getBudgetTo());

		if (null != propertyValuesDTO.getPossession())
			dbPropertyValueDTO.setPossession(propertyValuesDTO.getPossession());

		if (null != propertyValuesDTO.getPropertyLink())
			dbPropertyValueDTO.setPropertyLink(propertyValuesDTO.getPropertyLink());

		dbPropertyValueDTO.setUpdatedBy(propertyValuesDTO.getUpdatedBy());
		dbPropertyValueDTO.setUpdatedDate(propertyValuesDTO.getUpdatedDate());
		propertyValuesDao.savePropertyValue(dbPropertyValueDTO);
		LOGGER.info("PropertyValues " + propertyValuesDTO.getId() + " updated successfully by "
				+ propertyValuesDTO.getUpdatedBy());
	}

	@Override
	public PropertyValues deletePropertyValue(PropertyValuesDTO propertyValuesDTO) {
		PropertyValues dbPropertyValue = propertyValuesDao.getPropertyValueById(propertyValuesDTO.getId());
		PropertyValuesDTO dbPropertyValueDTO = PropertyValuesConverter
				.getPropertyValueDTOByPropertyValue(dbPropertyValue);

		dbPropertyValueDTO.setStatus(Constant.STATUS_INACTIVE);
		return propertyValuesDao.savePropertyValue(dbPropertyValueDTO);

	}

	@Override
	public List<PropertyValues> getPropertyValueData(PropertyValuesDTO propertyValuesDTO) {
		return propertyValuesDao.getPropertyValueData(propertyValuesDTO);
	}

	@Override
	public List<Map<String, Object>> getPropertyValues(PropertyValuesDTO propertyValuesDTO) {
		return propertyValuesDao.getPropertyValues(propertyValuesDTO);
	}

}
