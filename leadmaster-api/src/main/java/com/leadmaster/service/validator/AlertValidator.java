package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.AlertDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class AlertValidator implements Validator {

	private static final String BAD_REQUEST_ERROR_CD = Constant.BAD_REQUEST_ERROR_CD;
	private static final Pattern VALID_EAMIL_PATTERN = Pattern.compile(Constant.EMAIL_PATTERN);
	private static final Pattern MOBILE_PATTERN = Pattern.compile(Constant.MOBILE_PATTERN);

	private static final List<String> VALID_UPDATE_STATUS = Arrays.asList(Constant.STATUS_ACTIVE,
			Constant.STATUS_DELEATED);

	@Autowired
	private UserUtils userUtils;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

	// amenities
	public void saveAlert(AlertDTO alertDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(alertDTO.getAlertText()))
			errors.rejectValue("alertText", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(alertDTO.getBranch()))
			errors.rejectValue("branch", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(alertDTO.getRole()))
			errors.rejectValue("role", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		alertDTO.setAlertStatus(Constant.STATUS_ACTIVE);
		alertDTO.setStatus(Constant.STATUS_ACTIVE);
		alertDTO.setCreatedDate(createdTime);
		alertDTO.setCreatedBy(logedUserid);

	}

	public void getAllAlerts(AlertDTO alertDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == alertDTO.getStatus())
			alertDTO.setStatus(Constant.STATUS_ACTIVE);

		alertDTO.setUpdatedBy(logedUserid);
		alertDTO.setUpdatedDate(createdTime);
	}

	public void getAlertById(AlertDTO alertDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(alertDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		alertDTO.setUpdatedBy(logedUserid);
		alertDTO.setUpdatedDate(createdTime);
	}

	public void updateAlert(AlertDTO alertDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(alertDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != alertDTO.getAlertText() && CustomValidator.isEmpty(alertDTO.getAlertText()))
			errors.rejectValue("alertText", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != alertDTO.getBranch() && CustomValidator.isEmpty(alertDTO.getBranch()))
			errors.rejectValue("branch", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != alertDTO.getRole() && CustomValidator.isEmpty(alertDTO.getRole()))
			errors.rejectValue("role", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != alertDTO.getAlertStatus() && CustomValidator.isEmpty(alertDTO.getAlertStatus()))
			errors.rejectValue("alertStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		alertDTO.setUpdatedBy(logedUserid);
		alertDTO.setUpdatedDate(createdTime);

	}

	public void deleteAlert(AlertDTO alertDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(alertDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			alertDTO.setUpdatedDate(createdDate);
			alertDTO.setUpdatedBy(logedUserid);
		}
	}

}
