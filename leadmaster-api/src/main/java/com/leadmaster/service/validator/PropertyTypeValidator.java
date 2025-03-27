package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.PropertyTypeDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class PropertyTypeValidator implements Validator {

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

	// propertyType
	public void savePropertyType(PropertyTypeDTO propertyTypeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyTypeDTO.getPropertyType()))
			errors.rejectValue("propertyType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyTypeDTO.setStatus(Constant.STATUS_ACTIVE);
		propertyTypeDTO.setCreatedDate(createdTime);
		propertyTypeDTO.setCreatedBy(logedUserid);

	}

	public void getAllPropertyTypes(PropertyTypeDTO propertyTypeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == propertyTypeDTO.getStatus())
			propertyTypeDTO.setStatus(Constant.STATUS_ACTIVE);

		propertyTypeDTO.setUpdatedBy(logedUserid);
		propertyTypeDTO.setUpdatedDate(createdTime);
	}

	public void getPropertyTypeById(PropertyTypeDTO propertyTypeDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyTypeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyTypeDTO.setUpdatedBy(logedUserid);
		propertyTypeDTO.setUpdatedDate(createdTime);
	}

	public void updatePropertyType(PropertyTypeDTO propertyTypeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyTypeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyTypeDTO.getPropertyType() && CustomValidator.isEmpty(propertyTypeDTO.getPropertyType()))
			errors.rejectValue("propertyType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyTypeDTO.setUpdatedBy(logedUserid);
		propertyTypeDTO.setUpdatedDate(createdTime);

	}

	public void deletePropertyType(PropertyTypeDTO propertyTypeDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(propertyTypeDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			propertyTypeDTO.setUpdatedDate(createdDate);
			propertyTypeDTO.setUpdatedBy(logedUserid);
		}
	}

}
