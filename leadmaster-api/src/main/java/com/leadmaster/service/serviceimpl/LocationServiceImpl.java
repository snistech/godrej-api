package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.LocationConverter;
import com.leadmaster.common.dao.LocationDao;
import com.leadmaster.common.domain.Location;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.LocationDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.LocationService;

@Service("LocationServiceImpl")
public class LocationServiceImpl implements LocationService {

	private static Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

	@Resource(name = "LocationDaoImpl")
	LocationDao locationDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveLocation(LocationDTO locationDTO) {
		List<Role> roles = loginService.getAllUserRoles(locationDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save Location Details.");

		// Check institute type already exists in db or not
		checkLocation(locationDTO.getLocation());

		locationDao.saveLocation(locationDTO);
		LOGGER.info("Location added successfully by " + locationDTO.getCreatedBy());
	}

	@Override
	public List<Location> getAllLocations(LocationDTO locationDTO) {
		return locationDao.getAllLocation(locationDTO);
	}

	@Override
	public Location getLocationById(LocationDTO locationDTO) {
		return locationDao.getLocationById(locationDTO.getId());
	}

	@Override
	public void updateLocation(LocationDTO locationDTO) {
		List<Role> roles = loginService.getAllUserRoles(locationDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Locations Details.");

		Location locations = locationDao.getLocationById(locationDTO.getId());
		LocationDTO dbLocationDTO = LocationConverter.getLocationDTOByLocation(locations);

		if (null != locationDTO.getSubLocation()
				&& !locationDTO.getSubLocation().equals(dbLocationDTO.getSubLocation())) {

			// Check institute type already exists in db or not
			checkLocation(locationDTO.getSubLocation());
			dbLocationDTO.setSubLocation(locationDTO.getSubLocation());
		}

		if (null != locationDTO.getLocation())
			dbLocationDTO.setLocation(locationDTO.getLocation());

		dbLocationDTO.setUpdatedBy(locationDTO.getUpdatedBy());
		dbLocationDTO.setUpdatedDate(locationDTO.getUpdatedDate());
		locationDao.saveLocation(dbLocationDTO);
		LOGGER.info("Location " + locationDTO.getId() + " updated successfully by " + locationDTO.getUpdatedBy());
	}

	private void checkLocation(String location) {
		List<Location> dbLocation = locationDao.getLocationByName(location);
		if (null != dbLocation && dbLocation.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Location already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public Location deleteLocation(LocationDTO locationDTO) {
		Location dbLocation = locationDao.getLocationById(locationDTO.getId());
		LocationDTO dbLocationDTO = LocationConverter.getLocationDTOByLocation(dbLocation);

		dbLocationDTO.setStatus(Constant.STATUS_INACTIVE);
		return locationDao.saveLocation(dbLocationDTO);

	}

	@Override
	public Map<String, Object> getLocationsCategoryWise(LocationDTO locationDTO) {
		return locationDao.getLocationsCategoryWise(locationDTO);
	}

}
