package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.ApartmentTypeDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class ApartmentTypeValidator implements Validator {

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

	// apartmentType
	public void saveApartmentType(ApartmentTypeDTO apartmentTypeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(apartmentTypeDTO.getType()))
			errors.rejectValue("type", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		apartmentTypeDTO.setStatus(Constant.STATUS_ACTIVE);
		apartmentTypeDTO.setCreatedDate(createdTime);
		apartmentTypeDTO.setCreatedBy(logedUserid);

	}

	public void getAllApartmentTypes(ApartmentTypeDTO apartmentTypeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == apartmentTypeDTO.getStatus())
			apartmentTypeDTO.setStatus(Constant.STATUS_ACTIVE);

		apartmentTypeDTO.setUpdatedBy(logedUserid);
		apartmentTypeDTO.setUpdatedDate(createdTime);
	}

	public void getApartmentTypeById(ApartmentTypeDTO apartmentTypeDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(apartmentTypeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		apartmentTypeDTO.setUpdatedBy(logedUserid);
		apartmentTypeDTO.setUpdatedDate(createdTime);
	}

	public void updateApartmentType(ApartmentTypeDTO apartmentTypeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(apartmentTypeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != apartmentTypeDTO.getType() && CustomValidator.isEmpty(apartmentTypeDTO.getType()))
			errors.rejectValue("type", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		apartmentTypeDTO.setUpdatedBy(logedUserid);
		apartmentTypeDTO.setUpdatedDate(createdTime);

	}

	public void deleteApartmentType(ApartmentTypeDTO apartmentTypeDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(apartmentTypeDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			apartmentTypeDTO.setUpdatedDate(createdDate);
			apartmentTypeDTO.setUpdatedBy(logedUserid);
		}
	}

}
