package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.PropertyApprovalDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class PropertyApprovalValidator implements Validator {

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
	public void savePropertyApproval(PropertyApprovalDTO propertyApprovalDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyApprovalDTO.getApproval()))
			errors.rejectValue("approval", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyApprovalDTO.setStatus(Constant.STATUS_ACTIVE);
		propertyApprovalDTO.setCreatedDate(createdTime);
		propertyApprovalDTO.setCreatedBy(logedUserid);

	}

	public void getAllPropertyApprovals(PropertyApprovalDTO propertyApprovalDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == propertyApprovalDTO.getStatus())
			propertyApprovalDTO.setStatus(Constant.STATUS_ACTIVE);

		propertyApprovalDTO.setUpdatedBy(logedUserid);
		propertyApprovalDTO.setUpdatedDate(createdTime);
	}

	public void getPropertyApprovalById(PropertyApprovalDTO propertyApprovalDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyApprovalDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyApprovalDTO.setUpdatedBy(logedUserid);
		propertyApprovalDTO.setUpdatedDate(createdTime);
	}

	public void updatePropertyApproval(PropertyApprovalDTO propertyApprovalDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyApprovalDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyApprovalDTO.getApproval() && CustomValidator.isEmpty(propertyApprovalDTO.getApproval()))
			errors.rejectValue("approval", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyApprovalDTO.setUpdatedBy(logedUserid);
		propertyApprovalDTO.setUpdatedDate(createdTime);

	}

	public void deletePropertyApproval(PropertyApprovalDTO propertyApprovalDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(propertyApprovalDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			propertyApprovalDTO.setUpdatedDate(createdDate);
			propertyApprovalDTO.setUpdatedBy(logedUserid);
		}
	}

}
