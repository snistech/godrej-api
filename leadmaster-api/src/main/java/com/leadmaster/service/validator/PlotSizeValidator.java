package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.PlotSizeDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class PlotSizeValidator implements Validator {

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
	public void savePlotSize(PlotSizeDTO plotSizeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(plotSizeDTO.getSize()))
			errors.rejectValue("size", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		plotSizeDTO.setStatus(Constant.STATUS_ACTIVE);
		plotSizeDTO.setCreatedDate(createdTime);
		plotSizeDTO.setCreatedBy(logedUserid);

	}

	public void getAllPlotSizes(PlotSizeDTO plotSizeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == plotSizeDTO.getStatus())
			plotSizeDTO.setStatus(Constant.STATUS_ACTIVE);

		plotSizeDTO.setUpdatedBy(logedUserid);
		plotSizeDTO.setUpdatedDate(createdTime);
	}

	public void getPlotSizeById(PlotSizeDTO plotSizeDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(plotSizeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		plotSizeDTO.setUpdatedBy(logedUserid);
		plotSizeDTO.setUpdatedDate(createdTime);
	}

	public void updatePlotSize(PlotSizeDTO plotSizeDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(plotSizeDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != plotSizeDTO.getSize() && CustomValidator.isEmpty(plotSizeDTO.getSize()))
			errors.rejectValue("size", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		plotSizeDTO.setUpdatedBy(logedUserid);
		plotSizeDTO.setUpdatedDate(createdTime);

	}

	public void deletePlotSize(PlotSizeDTO plotSizeDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(plotSizeDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			plotSizeDTO.setUpdatedDate(createdDate);
			plotSizeDTO.setUpdatedBy(logedUserid);
		}
	}

}
