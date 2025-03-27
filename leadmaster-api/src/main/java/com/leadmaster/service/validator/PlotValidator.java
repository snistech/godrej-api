package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.PlotDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class PlotValidator implements Validator {

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
	public void savePlot(PlotDTO plotDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(plotDTO.getPlotName()))
			errors.rejectValue("plotName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		plotDTO.setStatus(Constant.STATUS_ACTIVE);
		plotDTO.setCreatedDate(createdTime);
		plotDTO.setCreatedBy(logedUserid);

	}

	public void getAllPlots(PlotDTO plotDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == plotDTO.getStatus())
			plotDTO.setStatus(Constant.STATUS_ACTIVE);

		plotDTO.setUpdatedBy(logedUserid);
		plotDTO.setUpdatedDate(createdTime);
	}

	public void getPlotById(PlotDTO plotDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(plotDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		plotDTO.setUpdatedBy(logedUserid);
		plotDTO.setUpdatedDate(createdTime);
	}

	public void updatePlot(PlotDTO plotDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(plotDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != plotDTO.getPlotName() && CustomValidator.isEmpty(plotDTO.getPlotName()))
			errors.rejectValue("plotName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		plotDTO.setUpdatedBy(logedUserid);
		plotDTO.setUpdatedDate(createdTime);

	}

	public void deletePlot(PlotDTO plotDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(plotDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			plotDTO.setUpdatedDate(createdDate);
			plotDTO.setUpdatedBy(logedUserid);
		}
	}

}
