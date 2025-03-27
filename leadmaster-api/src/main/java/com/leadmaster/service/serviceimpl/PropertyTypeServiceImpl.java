package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.PropertyTypeConverter;
import com.leadmaster.common.dao.PropertyTypeDao;
import com.leadmaster.common.domain.PropertyType;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.PropertyTypeDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.PropertyTypeService;

@Service("PropertyTypeServiceImpl")
public class PropertyTypeServiceImpl implements PropertyTypeService {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertyTypeServiceImpl.class);

	@Resource(name = "PropertyTypeDaoImpl")
	PropertyTypeDao propertyTypeDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void savePropertyType(PropertyTypeDTO propertyTypeDTO) {
		List<Role> roles = loginService.getAllUserRoles(propertyTypeDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save PropertyType Details.");

		// Check institute type already exists in db or not
		checkPropertyTypeName(propertyTypeDTO.getPropertyType());

		propertyTypeDao.savePropertyType(propertyTypeDTO);
		LOGGER.info("PropertyType added successfully by " + propertyTypeDTO.getCreatedBy());
	}

	@Override
	public List<PropertyType> getAllPropertyTypes(PropertyTypeDTO propertyTypeDTO) {
		return propertyTypeDao.getAllPropertyType(propertyTypeDTO);
	}

	@Override
	public PropertyType getPropertyTypeById(PropertyTypeDTO propertyTypeDTO) {
		return propertyTypeDao.getPropertyTypeById(propertyTypeDTO.getId());
	}

	@Override
	public void updatePropertyType(PropertyTypeDTO propertyTypeDTO) {
		List<Role> roles = loginService.getAllUserRoles(propertyTypeDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		PropertyType propertyTypes = propertyTypeDao.getPropertyTypeById(propertyTypeDTO.getId());
		PropertyTypeDTO dbCollegeDTO = PropertyTypeConverter.getPropertyTypeDTOByPropertyType(propertyTypes);

		if (null != propertyTypeDTO.getPropertyType()
				&& !propertyTypeDTO.getPropertyType().equals(dbCollegeDTO.getPropertyType())) {

			// Check institute type already exists in db or not
			checkPropertyTypeName(propertyTypeDTO.getPropertyType());
			dbCollegeDTO.setPropertyType(propertyTypeDTO.getPropertyType());
		}

		dbCollegeDTO.setUpdatedBy(propertyTypeDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(propertyTypeDTO.getUpdatedDate());
		propertyTypeDao.savePropertyType(dbCollegeDTO);
		LOGGER.info("PropertyType " + propertyTypeDTO.getId() + " updated successfully by "
				+ propertyTypeDTO.getUpdatedBy());
	}

	private void checkPropertyTypeName(String type) {
		List<PropertyType> dbCollege = propertyTypeDao.getPropertyTypeByType(type);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "PropertyType already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public PropertyType deletePropertyType(PropertyTypeDTO propertyTypeDTO) {
		PropertyType dbCollege = propertyTypeDao.getPropertyTypeById(propertyTypeDTO.getId());
		PropertyTypeDTO dbCollegeDTO = PropertyTypeConverter.getPropertyTypeDTOByPropertyType(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return propertyTypeDao.savePropertyType(dbCollegeDTO);

	}

}
