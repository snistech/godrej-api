package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.AssignedLeadDTO;
import com.leadmaster.common.dto.LeadDTO;
import com.leadmaster.common.dto.VisitedLeadsDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class LeadValidator implements Validator {

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

	// leads
	public void saveLead(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		String leadStatus = "Fresh";

		String logedInUser = userUtils.getLogedInUser().toString();

		if (CustomValidator.isEmpty(leadDTO.getFullName()))
			errors.rejectValue("fullName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (!CustomValidator.isValidPattern(MOBILE_PATTERN, leadDTO.getPhoneNumber()))
			errors.rejectValue("phoneNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadDTO.setDataCollector(logedUserid);
		leadDTO.setAssignedTo(logedInUser);
		leadDTO.setStatus(Constant.STATUS_ACTIVE);
		leadDTO.setCreatedDate(createdTime);
		leadDTO.setCreatedBy(logedUserid);

	}

	public void getAllLeads(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void getLeadById(LeadDTO leadDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(leadDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void updateLead(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(leadDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getFullName() && CustomValidator.isEmpty(leadDTO.getFullName()))
			errors.rejectValue("fullName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getAlternativeNumber() && CustomValidator.isEmpty(leadDTO.getAlternativeNumber()))
			errors.rejectValue("alternativeNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getLocation() && CustomValidator.isEmpty(leadDTO.getLocation()))
			errors.rejectValue("location", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getSubLocation() && CustomValidator.isEmpty(leadDTO.getSubLocation()))
			errors.rejectValue("subLocation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getLookingFor() && CustomValidator.isEmpty(leadDTO.getLookingFor()))
			errors.rejectValue("lookingFor", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getTenantType() && CustomValidator.isEmpty(leadDTO.getTenantType()))
			errors.rejectValue("tenantType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getApartmentType() && CustomValidator.isEmpty(leadDTO.getApartmentType()))
			errors.rejectValue("apartmentType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getAmenities() && CustomValidator.isEmpty(leadDTO.getAmenities()))
			errors.rejectValue("amenities", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getPropertyApproval() && CustomValidator.isEmpty(leadDTO.getPropertyApproval()))
			errors.rejectValue("propertyApproval", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getPlotSize() && CustomValidator.isEmpty(leadDTO.getPlotSize()))
			errors.rejectValue("plotSize", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getSuggestedPlot() && CustomValidator.isEmpty(leadDTO.getSuggestedPlot()))
			errors.rejectValue("suggestedPlot", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getBudgetRange() && CustomValidator.isEmpty(leadDTO.getBudgetRange()))
			errors.rejectValue("budgetRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getCustomerOccupation() && CustomValidator.isEmpty(leadDTO.getCustomerOccupation()))
			errors.rejectValue("customerOccupation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getIncomeRange() && CustomValidator.isEmpty(leadDTO.getIncomeRange()))
			errors.rejectValue("incomeRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getPossession() && CustomValidator.isEmpty(leadDTO.getPossession()))
			errors.rejectValue("possession", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getRemarks() && CustomValidator.isEmpty(leadDTO.getRemarks()))
			errors.rejectValue("remarks", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getLeadStatus() && CustomValidator.isEmpty(leadDTO.getLeadStatus()))
			errors.rejectValue("leadStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getAssignedTo() && CustomValidator.isEmpty(leadDTO.getAssignedTo()))
			errors.rejectValue("assignedTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getAssignedBy() && CustomValidator.isEmpty(leadDTO.getAssignedBy()))
			errors.rejectValue("assignedBy", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getDataCollector() && CustomValidator.isEmpty(leadDTO.getDataCollector()))
			errors.rejectValue("dataCollector", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getVisitingDate() && CustomValidator.isEmpty(leadDTO.getVisitingDate()))
			errors.rejectValue("visitingDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getVisitedDate() && CustomValidator.isEmpty(leadDTO.getVisitedDate()))
			errors.rejectValue("visitedDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getConversionDate() && CustomValidator.isEmpty(leadDTO.getConversionDate()))
			errors.rejectValue("conversionDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getFinalizedCounsellor() && CustomValidator.isEmpty(leadDTO.getFinalizedCounsellor()))
			errors.rejectValue("finalizedCounsellor", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getFinalizedProperty() && CustomValidator.isEmpty(leadDTO.getFinalizedProperty()))
			errors.rejectValue("finalizedProperty", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getFinalizedProof() && CustomValidator.isEmpty(leadDTO.getFinalizedProof()))
			errors.rejectValue("finalizedProof", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != leadDTO.getFinalizedStatus() && CustomValidator.isEmpty(leadDTO.getFinalizedStatus()))
			errors.rejectValue("finalizedStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);

	}

	public void assignLeads(LeadDTO leadDTO, Errors errors) {
		Long loggedInUserId = userUtils.getLogedInUser();
		String currentDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (leadDTO.getAssignedTo() == null) {
			errors.rejectValue("assignedTo", BAD_REQUEST_ERROR_CD, "AssignedTo field is empty or not in valid format");
		}

		if (CustomValidator.isEmpty(leadDTO.getId())) {
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "id field is empty or not in valid format");
		}

		leadDTO.setAssignedBy(loggedInUserId);
		leadDTO.setUpdatedDate(currentDate);
		leadDTO.setUpdatedBy(loggedInUserId);
	}

	public void updateAssignLead(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(leadDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(leadDTO.getAssignedTo()))
			errors.rejectValue("assignedTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);

	}

	public void getFollowupData(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void getFreshData(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void getLeadTrack(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void getVisitedLeads(VisitedLeadsDTO visitedLeadsDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

//		if (null == visitedLeadsDTO.getStatus())
//			visitedLeadsDTO.setStatus(Constant.STATUS_ACTIVE);

		visitedLeadsDTO.setUpdatedBy(logedUserid);
		visitedLeadsDTO.setUpdatedDate(createdTime);
	}

	public void getAssignedLeads(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		if (CustomValidator.isEmpty(leadDTO.getAssignedTo()))
			errors.rejectValue("assignedTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void getCollegeWiseData(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		if (CustomValidator.isEmpty(leadDTO.getSuggestedPlot()))
			errors.rejectValue("suggestedPlot", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void getAllAssignedLeads(AssignedLeadDTO assignedLeadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == assignedLeadDTO.getStatus())
			assignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);

		assignedLeadDTO.setUpdatedBy(logedUserid);
		assignedLeadDTO.setUpdatedDate(createdTime);
	}

	public void getAllLevelThreeLeads(AssignedLeadDTO assignedLeadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == assignedLeadDTO.getStatus())
			assignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);

		assignedLeadDTO.setUpdatedBy(logedUserid);
		assignedLeadDTO.setUpdatedDate(createdTime);
	}

	public void getAssignedLeadById(AssignedLeadDTO assignedLeadDTO, Errors errors) {

		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(assignedLeadDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		assignedLeadDTO.setUpdatedBy(logedUserid);
		assignedLeadDTO.setUpdatedDate(createdTime);
	}

	public void updateAssignedLead(AssignedLeadDTO assignLeadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(assignLeadDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getFullName() && CustomValidator.isEmpty(assignLeadDTO.getFullName()))
			errors.rejectValue("fullName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getAlternativeNumber()
				&& CustomValidator.isEmpty(assignLeadDTO.getAlternativeNumber()))
			errors.rejectValue("alternativeNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getLocation() && CustomValidator.isEmpty(assignLeadDTO.getLocation()))
			errors.rejectValue("location", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getLookingFor() && CustomValidator.isEmpty(assignLeadDTO.getLookingFor()))
			errors.rejectValue("lookingFor", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getTenantType() && CustomValidator.isEmpty(assignLeadDTO.getTenantType()))
			errors.rejectValue("tenantType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getApartmentType() && CustomValidator.isEmpty(assignLeadDTO.getApartmentType()))
			errors.rejectValue("apartmentType", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getAmenities() && CustomValidator.isEmpty(assignLeadDTO.getAmenities()))
			errors.rejectValue("amenities", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getPropertyApproval() && CustomValidator.isEmpty(assignLeadDTO.getPropertyApproval()))
			errors.rejectValue("propertyApproval", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getPlotSize() && CustomValidator.isEmpty(assignLeadDTO.getPlotSize()))
			errors.rejectValue("plotSize", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getSuggestedPlot() && CustomValidator.isEmpty(assignLeadDTO.getSuggestedPlot()))
			errors.rejectValue("suggestedPlot", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getBudgetRange() && CustomValidator.isEmpty(assignLeadDTO.getBudgetRange()))
			errors.rejectValue("budgetRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getCustomerOccupation()
				&& CustomValidator.isEmpty(assignLeadDTO.getCustomerOccupation()))
			errors.rejectValue("customerOccupation", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getIncomeRange() && CustomValidator.isEmpty(assignLeadDTO.getIncomeRange()))
			errors.rejectValue("incomeRange", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getPossession() && CustomValidator.isEmpty(assignLeadDTO.getPossession()))
			errors.rejectValue("possession", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getRemarks() && CustomValidator.isEmpty(assignLeadDTO.getRemarks()))
			errors.rejectValue("remarks", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getLeadStatus() && CustomValidator.isEmpty(assignLeadDTO.getLeadStatus()))
			errors.rejectValue("leadStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getAssignedTo() && CustomValidator.isEmpty(assignLeadDTO.getAssignedTo()))
			errors.rejectValue("assignedTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getAssignedBy() && CustomValidator.isEmpty(assignLeadDTO.getAssignedBy()))
			errors.rejectValue("assignedBy", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getDataCollector() && CustomValidator.isEmpty(assignLeadDTO.getDataCollector()))
			errors.rejectValue("dataCollector", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getVisitingDate() && CustomValidator.isEmpty(assignLeadDTO.getVisitingDate()))
			errors.rejectValue("visitingDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getVisitedDate() && CustomValidator.isEmpty(assignLeadDTO.getVisitedDate()))
			errors.rejectValue("visitedDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getConversionDate() && CustomValidator.isEmpty(assignLeadDTO.getConversionDate()))
			errors.rejectValue("conversionDate", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getFinalizedCounsellor()
				&& CustomValidator.isEmpty(assignLeadDTO.getFinalizedCounsellor()))
			errors.rejectValue("finalizedCounsellor", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getFinalizedProperty()
				&& CustomValidator.isEmpty(assignLeadDTO.getFinalizedProperty()))
			errors.rejectValue("finalizedProperty", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getFinalizedProof() && CustomValidator.isEmpty(assignLeadDTO.getFinalizedProof()))
			errors.rejectValue("finalizedProof", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getFinalizedStatus() && CustomValidator.isEmpty(assignLeadDTO.getFinalizedStatus()))
			errors.rejectValue("finalizedStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getAssignedTo() && CustomValidator.isEmpty(assignLeadDTO.getAssignedTo()))
			errors.rejectValue("assignedTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != assignLeadDTO.getAssignedBy() && CustomValidator.isEmpty(assignLeadDTO.getAssignedBy()))
			errors.rejectValue("assignedBy", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		assignLeadDTO.setUpdatedBy(logedUserid);
		assignLeadDTO.setUpdatedDate(createdTime);

	}

	public void getAllLeadsCount(LeadDTO leadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == leadDTO.getStatus())
			leadDTO.setStatus(Constant.STATUS_ACTIVE);

		leadDTO.setUpdatedBy(logedUserid);
		leadDTO.setUpdatedDate(createdTime);
	}

	public void saveNewAssignedLead(AssignedLeadDTO assignedLeadDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		String leadStatus = "Assigned";

		if (CustomValidator.isEmpty(assignedLeadDTO.getFullName()))
			errors.rejectValue("fullName", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (!CustomValidator.isValidPattern(MOBILE_PATTERN, assignedLeadDTO.getPhoneNumber()))
			errors.rejectValue("phoneNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		assignedLeadDTO.setLeadStatus(leadStatus);
		assignedLeadDTO.setAssignedBy(logedUserid);
		assignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);
		assignedLeadDTO.setCreatedDate(createdTime);
		assignedLeadDTO.setCreatedBy(logedUserid);

	}

}