package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.PropertyApprovalConverter;
import com.leadmaster.common.dao.PropertyApprovalDao;
import com.leadmaster.common.domain.PropertyApproval;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.PropertyApprovalDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.PropertyApprovalService;

@Service("PropertyApprovalServiceImpl")
public class PropertyApprovalServiceImpl implements PropertyApprovalService {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertyApprovalServiceImpl.class);

	@Resource(name = "PropertyApprovalDaoImpl")
	PropertyApprovalDao propertyApprovalDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void savePropertyApproval(PropertyApprovalDTO propertyApprovalDTO) {
		List<Role> roles = loginService.getAllUserRoles(propertyApprovalDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save PropertyApproval Details.");

		// Check institute type already exists in db or not
		checkPropertyApproval(propertyApprovalDTO.getApproval());

		propertyApprovalDao.savePropertyApproval(propertyApprovalDTO);
		LOGGER.info("PropertyApproval added successfully by " + propertyApprovalDTO.getCreatedBy());
	}

	@Override
	public List<PropertyApproval> getAllPropertyApprovals(PropertyApprovalDTO propertyApprovalDTO) {
		return propertyApprovalDao.getAllPropertyApproval(propertyApprovalDTO);
	}

	@Override
	public PropertyApproval getPropertyApprovalById(PropertyApprovalDTO propertyApprovalDTO) {
		return propertyApprovalDao.getPropertyApprovalById(propertyApprovalDTO.getId());
	}

	@Override
	public void updatePropertyApproval(PropertyApprovalDTO propertyApprovalDTO) {
		List<Role> roles = loginService.getAllUserRoles(propertyApprovalDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		PropertyApproval propertyApprovals = propertyApprovalDao.getPropertyApprovalById(propertyApprovalDTO.getId());
		PropertyApprovalDTO dbCollegeDTO = PropertyApprovalConverter
				.getPropertyApprovalDTOByPropertyApproval(propertyApprovals);

		if (null != propertyApprovalDTO.getApproval()
				&& !propertyApprovalDTO.getApproval().equals(dbCollegeDTO.getApproval())) {

			// Check institute type already exists in db or not
			checkPropertyApproval(propertyApprovalDTO.getApproval());
			dbCollegeDTO.setApproval(propertyApprovalDTO.getApproval());
		}

		dbCollegeDTO.setUpdatedBy(propertyApprovalDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(propertyApprovalDTO.getUpdatedDate());
		propertyApprovalDao.savePropertyApproval(dbCollegeDTO);
		LOGGER.info("PropertyApproval " + propertyApprovalDTO.getId() + " updated successfully by "
				+ propertyApprovalDTO.getUpdatedBy());
	}

	private void checkPropertyApproval(String approval) {
		List<PropertyApproval> dbCollege = propertyApprovalDao.getPropertyByApproval(approval);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "PropertyApproval already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public PropertyApproval deletePropertyApproval(PropertyApprovalDTO propertyApprovalDTO) {
		PropertyApproval dbCollege = propertyApprovalDao.getPropertyApprovalById(propertyApprovalDTO.getId());
		PropertyApprovalDTO dbCollegeDTO = PropertyApprovalConverter
				.getPropertyApprovalDTOByPropertyApproval(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return propertyApprovalDao.savePropertyApproval(dbCollegeDTO);

	}

}
