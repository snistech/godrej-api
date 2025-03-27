package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.LeadTrackDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class LeadTrackValidator implements Validator {

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

	public void saveLeadTrack(LeadTrackDTO leadTrackDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(leadTrackDTO.getLeadId()))
			errors.rejectValue("leadId", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(leadTrackDTO.getFollowupDate()))
			errors.rejectValue("followupDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(leadTrackDTO.getLeadStatus()))
			errors.rejectValue("leadStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadTrackDTO.setStatus(Constant.STATUS_ACTIVE);
		leadTrackDTO.setCreatedDate(createdTime);
		leadTrackDTO.setCreatedBy(logedUserid);

	}

	public void getAllLeadTracks(LeadTrackDTO leadTrackDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(leadTrackDTO.getLeadId()))
			errors.rejectValue("leadId", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null == leadTrackDTO.getStatus())
			leadTrackDTO.setStatus(Constant.STATUS_ACTIVE);

		leadTrackDTO.setUpdatedBy(logedUserid);
		leadTrackDTO.setUpdatedDate(createdTime);
	}

}
