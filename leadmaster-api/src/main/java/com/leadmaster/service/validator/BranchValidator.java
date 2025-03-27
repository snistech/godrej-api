package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.BranchDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class BranchValidator implements Validator {

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
	public void saveBranch(BranchDTO branchDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(branchDTO.getBranch()))
			errors.rejectValue("branch", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		branchDTO.setStatus(Constant.STATUS_ACTIVE);
		branchDTO.setCreatedDate(createdTime);
		branchDTO.setCreatedBy(logedUserid);

	}

	public void getAllBranchs(BranchDTO branchDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == branchDTO.getStatus())
			branchDTO.setStatus(Constant.STATUS_ACTIVE);

		branchDTO.setUpdatedBy(logedUserid);
		branchDTO.setUpdatedDate(createdTime);
	}

	public void getBranchById(BranchDTO branchDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(branchDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		branchDTO.setUpdatedBy(logedUserid);
		branchDTO.setUpdatedDate(createdTime);
	}

	public void updateBranch(BranchDTO branchDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(branchDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != branchDTO.getBranch() && CustomValidator.isEmpty(branchDTO.getBranch()))
			errors.rejectValue("branch", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		branchDTO.setUpdatedBy(logedUserid);
		branchDTO.setUpdatedDate(createdTime);

	}

	public void deleteBranch(BranchDTO branchDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(branchDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			branchDTO.setUpdatedDate(createdDate);
			branchDTO.setUpdatedBy(logedUserid);
		}
	}

}
