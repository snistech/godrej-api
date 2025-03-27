package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.LocationDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class LocationValidator implements Validator {

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
	public void saveLocation(LocationDTO locationDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(locationDTO.getLocation()))
			errors.rejectValue("location", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(locationDTO.getSubLocation()))
			errors.rejectValue("subLocation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		locationDTO.setStatus(Constant.STATUS_ACTIVE);
		locationDTO.setCreatedDate(createdTime);
		locationDTO.setCreatedBy(logedUserid);

	}

	public void getAllLocations(LocationDTO locationDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == locationDTO.getStatus())
			locationDTO.setStatus(Constant.STATUS_ACTIVE);

		locationDTO.setUpdatedBy(logedUserid);
		locationDTO.setUpdatedDate(createdTime);
	}

	public void getLocationById(LocationDTO locationDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(locationDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		locationDTO.setUpdatedBy(logedUserid);
		locationDTO.setUpdatedDate(createdTime);
	}

	public void updateLocation(LocationDTO locationDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(locationDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != locationDTO.getLocation() && CustomValidator.isEmpty(locationDTO.getLocation()))
			errors.rejectValue("location", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != locationDTO.getSubLocation() && CustomValidator.isEmpty(locationDTO.getSubLocation()))
			errors.rejectValue("subLocation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		locationDTO.setUpdatedBy(logedUserid);
		locationDTO.setUpdatedDate(createdTime);

	}

	public void deleteLocation(LocationDTO locationDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(locationDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			locationDTO.setUpdatedDate(createdDate);
			locationDTO.setUpdatedBy(logedUserid);
		}
	}

	public void getLocationsCategoryWise(LocationDTO locationDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		locationDTO.setStatus(Constant.STATUS_ACTIVE);
		locationDTO.setUpdatedDate(createdDate);
		locationDTO.setUpdatedBy(logedUserid);
	}

}
