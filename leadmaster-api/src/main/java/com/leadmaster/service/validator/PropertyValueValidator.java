package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.PropertyValuesDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class PropertyValueValidator implements Validator {

	private static final String BAD_REQUEST_ERROR_CD = Constant.BAD_REQUEST_ERROR_CD;

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

	public void savePropertyValue(PropertyValuesDTO propertyValuesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyValuesDTO.getCategory()))
			errors.rejectValue("category", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getType()))
			errors.rejectValue("type", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getProjectName()))
			errors.rejectValue("projectName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getLocation()))
			errors.rejectValue("location", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getSubLocation()))
			errors.rejectValue("subLocation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getApartmentType()))
			errors.rejectValue("apartmentType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getLookingFor()))
			errors.rejectValue("lookingFor", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getBudgetFrom()))
			errors.rejectValue("budgetFrom", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getBudgetTo()))
			errors.rejectValue("budgetTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getPossession()))
			errors.rejectValue("possession", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(propertyValuesDTO.getPropertyLink()))
			errors.rejectValue("propertyLink", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyValuesDTO.setStatus(Constant.STATUS_ACTIVE);
		propertyValuesDTO.setCreatedDate(createdTime);
		propertyValuesDTO.setCreatedBy(logedUserid);

	}

	public void getAllPropertyValues(PropertyValuesDTO propertyValuesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == propertyValuesDTO.getStatus())
			propertyValuesDTO.setStatus(Constant.STATUS_ACTIVE);

		propertyValuesDTO.setUpdatedBy(logedUserid);
		propertyValuesDTO.setUpdatedDate(createdTime);
	}

	public void getPropertyValueById(PropertyValuesDTO propertyValuesDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyValuesDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyValuesDTO.setUpdatedBy(logedUserid);
		propertyValuesDTO.setUpdatedDate(createdTime);
	}

	public void updatePropertyValue(PropertyValuesDTO propertyValuesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(propertyValuesDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getCategory() && CustomValidator.isEmpty(propertyValuesDTO.getCategory()))
			errors.rejectValue("category", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getType() && CustomValidator.isEmpty(propertyValuesDTO.getType()))
			errors.rejectValue("type", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getProjectName() && CustomValidator.isEmpty(propertyValuesDTO.getProjectName()))
			errors.rejectValue("projectName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getLocation() && CustomValidator.isEmpty(propertyValuesDTO.getLocation()))
			errors.rejectValue("location", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getSubLocation() && CustomValidator.isEmpty(propertyValuesDTO.getSubLocation()))
			errors.rejectValue("subLocation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getApartmentType()
				&& CustomValidator.isEmpty(propertyValuesDTO.getApartmentType()))
			errors.rejectValue("apartmentType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getLookingFor() && CustomValidator.isEmpty(propertyValuesDTO.getLookingFor()))
			errors.rejectValue("lookingFor", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getBudgetFrom() && CustomValidator.isEmpty(propertyValuesDTO.getBudgetFrom()))
			errors.rejectValue("budgetFrom", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getBudgetTo() && CustomValidator.isEmpty(propertyValuesDTO.getBudgetTo()))
			errors.rejectValue("budgetTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != propertyValuesDTO.getPossession() && CustomValidator.isEmpty(propertyValuesDTO.getPossession()))
			errors.rejectValue("possession", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		propertyValuesDTO.setUpdatedBy(logedUserid);
		propertyValuesDTO.setUpdatedDate(createdTime);

	}

	public void deletePropertyValue(PropertyValuesDTO propertyValuesDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(propertyValuesDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			propertyValuesDTO.setUpdatedDate(createdDate);
			propertyValuesDTO.setUpdatedBy(logedUserid);
		}
	}

	public void getPropertyValueData(PropertyValuesDTO propertyValuesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == propertyValuesDTO.getStatus())
			propertyValuesDTO.setStatus(Constant.STATUS_ACTIVE);

		propertyValuesDTO.setUpdatedBy(logedUserid);
		propertyValuesDTO.setUpdatedDate(createdTime);
	}

	public void getPropertyValues(PropertyValuesDTO propertyValuesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == propertyValuesDTO.getStatus())
			propertyValuesDTO.setStatus(Constant.STATUS_ACTIVE);

		propertyValuesDTO.setUpdatedBy(logedUserid);
		propertyValuesDTO.setUpdatedDate(createdTime);
	}

}
