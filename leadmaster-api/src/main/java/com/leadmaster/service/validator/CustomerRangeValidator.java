package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.CustomerRangeDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class CustomerRangeValidator implements Validator {

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

	// customerRange
	public void saveCustomerRange(CustomerRangeDTO customerRangeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(customerRangeDTO.getCustomerRange()))
			errors.rejectValue("customerRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		customerRangeDTO.setStatus(Constant.STATUS_ACTIVE);
		customerRangeDTO.setCreatedDate(createdTime);
		customerRangeDTO.setCreatedBy(logedUserid);

	}

	public void getAllCustomerRanges(CustomerRangeDTO customerRangeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == customerRangeDTO.getStatus())
			customerRangeDTO.setStatus(Constant.STATUS_ACTIVE);

		customerRangeDTO.setUpdatedBy(logedUserid);
		customerRangeDTO.setUpdatedDate(createdTime);
	}

	public void getCustomerRangeById(CustomerRangeDTO customerRangeDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(customerRangeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		customerRangeDTO.setUpdatedBy(logedUserid);
		customerRangeDTO.setUpdatedDate(createdTime);
	}

	public void updateCustomerRange(CustomerRangeDTO customerRangeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(customerRangeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != customerRangeDTO.getCustomerRange() && CustomValidator.isEmpty(customerRangeDTO.getCustomerRange()))
			errors.rejectValue("customerRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		customerRangeDTO.setUpdatedBy(logedUserid);
		customerRangeDTO.setUpdatedDate(createdTime);

	}

	public void deleteCustomerRange(CustomerRangeDTO customerRangeDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(customerRangeDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			customerRangeDTO.setUpdatedDate(createdDate);
			customerRangeDTO.setUpdatedBy(logedUserid);
		}
	}

}
