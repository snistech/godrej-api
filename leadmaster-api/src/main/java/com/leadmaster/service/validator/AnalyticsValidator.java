package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.AnalyticsDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;

public class AnalyticsValidator implements Validator {

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

	public void getConversionAnalytics(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getFailureAnalytics(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getDataCoverage(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getTeamCalls(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getCollegeAdmissions(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getGraphData(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getCurrentLeadTracking(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getCallAverageDifference(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getGetMyCollegeAnalytics(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getIndividualFollowups(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

	public void getLevelOneFollowups(AnalyticsDTO analyticsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == analyticsDTO.getStatus())
//			analyticsDTO.setStatus(Constant.STATUS_ACTIVE);

//		analyticsDTO.setUpdatedBy(logedUserid);
//		analyticsDTO.setUpdatedDate(createdTime);
	}

}
