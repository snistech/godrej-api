package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.ApartmentTypeConverter;
import com.leadmaster.common.dao.ApartmentTypeDao;
import com.leadmaster.common.domain.ApartmentType;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.ApartmentTypeDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.ApartmentTypeService;

@Service("ApartmentTypeServiceImpl")
public class ApartmentTypeServiceImpl implements ApartmentTypeService {

	private static Logger LOGGER = LoggerFactory.getLogger(ApartmentTypeServiceImpl.class);

	@Resource(name = "ApartmentTypeDaoImpl")
	ApartmentTypeDao apartmentTypeDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveApartmentType(ApartmentTypeDTO apartmentTypeDTO) {
		List<Role> roles = loginService.getAllUserRoles(apartmentTypeDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save ApartmentType Details.");

		// Check institute type already exists in db or not
		checkApartmentTypeName(apartmentTypeDTO.getType());

		apartmentTypeDao.saveApartmentType(apartmentTypeDTO);
		LOGGER.info("ApartmentType added successfully by " + apartmentTypeDTO.getCreatedBy());
	}

	@Override
	public List<ApartmentType> getAllApartmentTypes(ApartmentTypeDTO apartmentTypeDTO) {
		return apartmentTypeDao.getAllApartmentType(apartmentTypeDTO);
	}

	@Override
	public ApartmentType getApartmentTypeById(ApartmentTypeDTO apartmentTypeDTO) {
		return apartmentTypeDao.getApartmentTypeById(apartmentTypeDTO.getId());
	}

	@Override
	public void updateApartmentType(ApartmentTypeDTO apartmentTypeDTO) {
		List<Role> roles = loginService.getAllUserRoles(apartmentTypeDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		ApartmentType apartmentTypes = apartmentTypeDao.getApartmentTypeById(apartmentTypeDTO.getId());
		ApartmentTypeDTO dbCollegeDTO = ApartmentTypeConverter.getApartmentTypeDTOByApartmentType(apartmentTypes);

		if (null != apartmentTypeDTO.getType() && !apartmentTypeDTO.getType().equals(dbCollegeDTO.getType())) {

			// Check institute type already exists in db or not
			checkApartmentTypeName(apartmentTypeDTO.getType());
			dbCollegeDTO.setType(apartmentTypeDTO.getType());
		}

		dbCollegeDTO.setUpdatedBy(apartmentTypeDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(apartmentTypeDTO.getUpdatedDate());
		apartmentTypeDao.saveApartmentType(dbCollegeDTO);
		LOGGER.info("ApartmentType " + apartmentTypeDTO.getId() + " updated successfully by "
				+ apartmentTypeDTO.getUpdatedBy());
	}

	private void checkApartmentTypeName(String type) {
		List<ApartmentType> dbCollege = apartmentTypeDao.getApartmentTypeByType(type);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "ApartmentType already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public ApartmentType deleteApartmentType(ApartmentTypeDTO apartmentTypeDTO) {
		ApartmentType dbCollege = apartmentTypeDao.getApartmentTypeById(apartmentTypeDTO.getId());
		ApartmentTypeDTO dbCollegeDTO = ApartmentTypeConverter.getApartmentTypeDTOByApartmentType(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return apartmentTypeDao.saveApartmentType(dbCollegeDTO);

	}

}
