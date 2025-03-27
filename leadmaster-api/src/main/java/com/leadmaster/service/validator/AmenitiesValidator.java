package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.AmenitiesDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class AmenitiesValidator implements Validator {

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
	public void saveAmenities(AmenitiesDTO amenitiesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(amenitiesDTO.getAmenity()))
			errors.rejectValue("amenity", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		amenitiesDTO.setStatus(Constant.STATUS_ACTIVE);
		amenitiesDTO.setCreatedDate(createdTime);
		amenitiesDTO.setCreatedBy(logedUserid);

	}

	public void getAllAmenitiess(AmenitiesDTO amenitiesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == amenitiesDTO.getStatus())
			amenitiesDTO.setStatus(Constant.STATUS_ACTIVE);

		amenitiesDTO.setUpdatedBy(logedUserid);
		amenitiesDTO.setUpdatedDate(createdTime);
	}

	public void getAmenitiesById(AmenitiesDTO amenitiesDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(amenitiesDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		amenitiesDTO.setUpdatedBy(logedUserid);
		amenitiesDTO.setUpdatedDate(createdTime);
	}

	public void updateAmenities(AmenitiesDTO amenitiesDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(amenitiesDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != amenitiesDTO.getAmenity() && CustomValidator.isEmpty(amenitiesDTO.getAmenity()))
			errors.rejectValue("amenity", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		amenitiesDTO.setUpdatedBy(logedUserid);
		amenitiesDTO.setUpdatedDate(createdTime);

	}

	public void deleteAmenities(AmenitiesDTO amenitiesDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(amenitiesDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			amenitiesDTO.setUpdatedDate(createdDate);
			amenitiesDTO.setUpdatedBy(logedUserid);
		}
	}

}
