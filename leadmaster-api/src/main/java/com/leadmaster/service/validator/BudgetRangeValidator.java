package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.BudgetRangeDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class BudgetRangeValidator implements Validator {

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

	// budgetRange
	public void saveBudgetRange(BudgetRangeDTO budgetRangeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(budgetRangeDTO.getBudgetRange()))
			errors.rejectValue("budgetRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		budgetRangeDTO.setStatus(Constant.STATUS_ACTIVE);
		budgetRangeDTO.setCreatedDate(createdTime);
		budgetRangeDTO.setCreatedBy(logedUserid);

	}

	public void getAllBudgetRanges(BudgetRangeDTO budgetRangeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == budgetRangeDTO.getStatus())
			budgetRangeDTO.setStatus(Constant.STATUS_ACTIVE);

		budgetRangeDTO.setUpdatedBy(logedUserid);
		budgetRangeDTO.setUpdatedDate(createdTime);
	}

	public void getBudgetRangeById(BudgetRangeDTO budgetRangeDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(budgetRangeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		budgetRangeDTO.setUpdatedBy(logedUserid);
		budgetRangeDTO.setUpdatedDate(createdTime);
	}

	public void updateBudgetRange(BudgetRangeDTO budgetRangeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(budgetRangeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != budgetRangeDTO.getBudgetRange() && CustomValidator.isEmpty(budgetRangeDTO.getBudgetRange()))
			errors.rejectValue("budgetRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		budgetRangeDTO.setUpdatedBy(logedUserid);
		budgetRangeDTO.setUpdatedDate(createdTime);

	}

	public void deleteBudgetRange(BudgetRangeDTO budgetRangeDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(budgetRangeDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			budgetRangeDTO.setUpdatedDate(createdDate);
			budgetRangeDTO.setUpdatedBy(logedUserid);
		}
	}

}
