package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.ProjectDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class ProjectValidator implements Validator {

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
	public void saveProject(ProjectDTO projectDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(projectDTO.getCategory()))
			errors.rejectValue("category", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(projectDTO.getType()))
			errors.rejectValue("type", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(projectDTO.getProjectName()))
			errors.rejectValue("projectName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		projectDTO.setStatus(Constant.STATUS_ACTIVE);
		projectDTO.setCreatedDate(createdTime);
		projectDTO.setCreatedBy(logedUserid);

	}

	public void getAllProjects(ProjectDTO projectDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == projectDTO.getStatus())
			projectDTO.setStatus(Constant.STATUS_ACTIVE);

		projectDTO.setUpdatedBy(logedUserid);
		projectDTO.setUpdatedDate(createdTime);
	}

	public void getProjectById(ProjectDTO projectDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(projectDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		projectDTO.setUpdatedBy(logedUserid);
		projectDTO.setUpdatedDate(createdTime);
	}

	public void updateProject(ProjectDTO projectDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(projectDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != projectDTO.getCategory() && CustomValidator.isEmpty(projectDTO.getCategory()))
			errors.rejectValue("category", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != projectDTO.getType() && CustomValidator.isEmpty(projectDTO.getType()))
			errors.rejectValue("type", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != projectDTO.getProjectName() && CustomValidator.isEmpty(projectDTO.getProjectName()))
			errors.rejectValue("projectName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		projectDTO.setUpdatedBy(logedUserid);
		projectDTO.setUpdatedDate(createdTime);

	}

	public void deleteProject(ProjectDTO projectDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(projectDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			projectDTO.setUpdatedDate(createdDate);
			projectDTO.setUpdatedBy(logedUserid);
		}
	}

}
