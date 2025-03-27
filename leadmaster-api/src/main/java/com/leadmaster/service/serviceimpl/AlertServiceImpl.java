package com.leadmaster.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.AlertConverter;
import com.leadmaster.common.dao.AlertDao;
import com.leadmaster.common.domain.Alert;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.AlertDTO;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.AlertService;

@Service("AlertServiceImpl")
public class AlertServiceImpl implements AlertService {

	private static Logger LOGGER = LoggerFactory.getLogger(AlertServiceImpl.class);

	@Resource(name = "AlertDaoImpl")
	AlertDao alertDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveAlert(AlertDTO alertDTO) {
		List<Role> roles = loginService.getAllUserRoles(alertDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()) || x.getRole().equals(RoleEnum.SUPER_HR.getRole())
				|| x.getRole().equals(RoleEnum.HR.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save Alert Details.");

		alertDao.saveAlert(alertDTO);
		LOGGER.info("Alert added successfully by " + alertDTO.getCreatedBy());
	}

	@Override
	public List<Alert> getAllAlerts(AlertDTO alertDTO) {
		return alertDao.getAllAlert(alertDTO);
	}

	@Override
	public Alert getAlertById(AlertDTO alertDTO) {
		return alertDao.getAlertById(alertDTO.getId());
	}

	@Override
	public void updateAlert(AlertDTO alertDTO) {
		List<Role> roles = loginService.getAllUserRoles(alertDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()) || x.getRole().equals(RoleEnum.SUPER_HR.getRole())
				|| x.getRole().equals(RoleEnum.HR.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Alerts Details.");

		Alert alerts = alertDao.getAlertById(alertDTO.getId());
		AlertDTO dbAlertDTO = AlertConverter.getAlertDTOByAlert(alerts);

		if (null != alertDTO.getAlertText())
			dbAlertDTO.setAlertText(alertDTO.getAlertText());

		if (null != alertDTO.getBranch())
			dbAlertDTO.setBranch(alertDTO.getBranch());

		if (null != alertDTO.getRole())
			dbAlertDTO.setRole(alertDTO.getRole());

		if (null != alertDTO.getAlertStatus())
			dbAlertDTO.setAlertStatus(alertDTO.getAlertStatus());

		dbAlertDTO.setUpdatedBy(alertDTO.getUpdatedBy());
		dbAlertDTO.setUpdatedDate(alertDTO.getUpdatedDate());
		alertDao.saveAlert(dbAlertDTO);
		LOGGER.info("Alert " + alertDTO.getId() + " updated successfully by " + alertDTO.getUpdatedBy());
	}

	@Override
	public Alert deleteAlert(AlertDTO alertDTO) {
		Alert dbAlert = alertDao.getAlertById(alertDTO.getId());
		AlertDTO dbAlertDTO = AlertConverter.getAlertDTOByAlert(dbAlert);

		dbAlertDTO.setStatus(Constant.STATUS_INACTIVE);
		return alertDao.saveAlert(dbAlertDTO);

	}

}
