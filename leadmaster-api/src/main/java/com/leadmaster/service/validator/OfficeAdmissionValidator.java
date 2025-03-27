package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.OfficeAdmissionDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class OfficeAdmissionValidator implements Validator {

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
	public void saveOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(officeAdmissionDTO.getFullName()))
			errors.rejectValue("fullName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getPhoneNumber()))
			errors.rejectValue("phoneNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getFatherName()))
			errors.rejectValue("fatherName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getMotherName()))
			errors.rejectValue("motherName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getAddress()))
			errors.rejectValue("address", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getCourse()))
			errors.rejectValue("course", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getAdmittedDate()))
			errors.rejectValue("admittedDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getAdmittedClg()))
			errors.rejectValue("admittedClg", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getTotalFees()))
			errors.rejectValue("totalFees", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getPaidFees()))
			errors.rejectValue("paidFees", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getPaymentStatus()))
			errors.rejectValue("paymentStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(officeAdmissionDTO.getAdmissionStatus()))
			errors.rejectValue("admissionStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		officeAdmissionDTO.setStatus(Constant.STATUS_ACTIVE);
		officeAdmissionDTO.setCreatedDate(createdTime);
		officeAdmissionDTO.setCreatedBy(logedUserid);

	}

	public void getAllOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == officeAdmissionDTO.getStatus())
			officeAdmissionDTO.setStatus(Constant.STATUS_ACTIVE);

		officeAdmissionDTO.setUpdatedBy(logedUserid);
		officeAdmissionDTO.setUpdatedDate(createdTime);
	}

	public void getOfficeAdmissionById(OfficeAdmissionDTO officeAdmissionDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(officeAdmissionDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		officeAdmissionDTO.setUpdatedBy(logedUserid);
		officeAdmissionDTO.setUpdatedDate(createdTime);
	}

	public void updateOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(officeAdmissionDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getAlternativeNumber()
				&& CustomValidator.isEmpty(officeAdmissionDTO.getAlternativeNumber()))
			errors.rejectValue("alternativeNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getEmail() && CustomValidator.isEmpty(officeAdmissionDTO.getEmail()))
			errors.rejectValue("email", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getAdharProof() && CustomValidator.isEmpty(officeAdmissionDTO.getAdharProof()))
			errors.rejectValue("adharProof", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getPaidFees() && CustomValidator.isEmpty(officeAdmissionDTO.getPaidFees()))
			errors.rejectValue("paidFees", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getPaymentStatus()
				&& CustomValidator.isEmpty(officeAdmissionDTO.getPaymentStatus()))
			errors.rejectValue("paymentStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getAdmissionStatus()
				&& CustomValidator.isEmpty(officeAdmissionDTO.getAdmissionStatus()))
			errors.rejectValue("admissionStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getApprovalStatus()
				&& CustomValidator.isEmpty(officeAdmissionDTO.getApprovalStatus()))
			errors.rejectValue("approvalStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != officeAdmissionDTO.getRemarks() && CustomValidator.isEmpty(officeAdmissionDTO.getRemarks()))
			errors.rejectValue("remarks", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		officeAdmissionDTO.setUpdatedBy(logedUserid);
		officeAdmissionDTO.setUpdatedDate(createdTime);

	}

	public void deleteOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.hasValidValue(officeAdmissionDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

			officeAdmissionDTO.setUpdatedDate(createdDate);
			officeAdmissionDTO.setUpdatedBy(logedUserid);
		}
	}

	public void getAllOfficeAdmissions(OfficeAdmissionDTO officeAdmissionDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == officeAdmissionDTO.getStatus())
			officeAdmissionDTO.setStatus(Constant.STATUS_ACTIVE);

		officeAdmissionDTO.setUpdatedBy(logedUserid);
		officeAdmissionDTO.setUpdatedDate(createdTime);
	}

}
