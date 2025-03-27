package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.AmenitiesConverter;
import com.leadmaster.common.dao.AmenitiesDao;
import com.leadmaster.common.domain.Amenities;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.AmenitiesDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.AmenitiesService;

@Service("AmenitiesServiceImpl")
public class AmenitiesServiceImpl implements AmenitiesService {

	private static Logger LOGGER = LoggerFactory.getLogger(AmenitiesServiceImpl.class);

	@Resource(name = "AmenitiesDaoImpl")
	AmenitiesDao amenitiesDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveAmenities(AmenitiesDTO amenitiesDTO) {
		List<Role> roles = loginService.getAllUserRoles(amenitiesDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save Amenities Details.");

		// Check institute type already exists in db or not
		checkAmenitiesName(amenitiesDTO.getAmenity());

		amenitiesDao.saveAmenities(amenitiesDTO);
		LOGGER.info("Amenities added successfully by " + amenitiesDTO.getCreatedBy());
	}

	@Override
	public List<Amenities> getAllAmenitiess(AmenitiesDTO amenitiesDTO) {
		return amenitiesDao.getAllAmenities(amenitiesDTO);
	}

	@Override
	public Amenities getAmenitiesById(AmenitiesDTO amenitiesDTO) {
		return amenitiesDao.getAmenitiesById(amenitiesDTO.getId());
	}

	@Override
	public void updateAmenities(AmenitiesDTO amenitiesDTO) {
		List<Role> roles = loginService.getAllUserRoles(amenitiesDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		Amenities amenitiess = amenitiesDao.getAmenitiesById(amenitiesDTO.getId());
		AmenitiesDTO dbCollegeDTO = AmenitiesConverter.getAmenitiesDTOByAmenities(amenitiess);

		if (null != amenitiesDTO.getAmenity() && !amenitiesDTO.getAmenity().equals(dbCollegeDTO.getAmenity())) {

			// Check institute type already exists in db or not
			checkAmenitiesName(amenitiesDTO.getAmenity());
			dbCollegeDTO.setAmenity(amenitiesDTO.getAmenity());
		}

		dbCollegeDTO.setUpdatedBy(amenitiesDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(amenitiesDTO.getUpdatedDate());
		amenitiesDao.saveAmenities(dbCollegeDTO);
		LOGGER.info("Amenities " + amenitiesDTO.getId() + " updated successfully by " + amenitiesDTO.getUpdatedBy());
	}

	private void checkAmenitiesName(String amenity) {
		List<Amenities> dbCollege = amenitiesDao.getAmenitiesByAmenity(amenity);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Amenities already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public Amenities deleteAmenities(AmenitiesDTO amenitiesDTO) {
		Amenities dbCollege = amenitiesDao.getAmenitiesById(amenitiesDTO.getId());
		AmenitiesDTO dbCollegeDTO = AmenitiesConverter.getAmenitiesDTOByAmenities(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return amenitiesDao.saveAmenities(dbCollegeDTO);

	}

}
