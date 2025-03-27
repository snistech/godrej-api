package com.leadmaster.service.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.AssignedLeadConverter;
import com.leadmaster.common.converter.LeadConverter;
import com.leadmaster.common.dao.AssignedLeadDao;
import com.leadmaster.common.dao.LeadDao;
import com.leadmaster.common.dao.UserDao;
import com.leadmaster.common.dao.VisitedLeadsDao;
import com.leadmaster.common.domain.AssignedLead;
import com.leadmaster.common.domain.Lead;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.AssignedLeadDTO;
import com.leadmaster.common.dto.LeadDTO;
import com.leadmaster.common.dto.VisitedLeadsDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.CustomValidator;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.LeadService;

@Service("LeadServiceImpl")
public class LeadServiceImpl implements LeadService {

	private static Logger LOGGER = LoggerFactory.getLogger(LeadServiceImpl.class);

	private Map<String, Integer> lastAssignedUserIndex = new HashMap<>();

	@Resource(name = "LeadDaoImpl")
	LeadDao leadDao;

	@Resource(name = "UserDaoImpl")
	UserDao userDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Resource(name = "VisitedLeadsDaoImpl")
	VisitedLeadsDao visitedLeadsDao;

	@Resource(name = "AssignedLeadDaoImpl")
	AssignedLeadDao assignedLeadDao;

	// save lead
	@Override
	public void saveLead(LeadDTO leadDTO) {
		checkLead(leadDTO.getPhoneNumber());

		List<Role> roles = loginService.getAllUserRoles(leadDTO.getCreatedBy());
		boolean isLevelThreeUser = roles.stream().anyMatch(role -> role.getRole().equals(RoleEnum.LEVEL_3.getRole()));

		Lead savedLead = leadDao.saveLead(leadDTO);
		LOGGER.info("Lead added successfully by " + leadDTO.getCreatedBy());

		if (isLevelThreeUser) {
			AssignedLeadDTO assignedLeadDTO = new AssignedLeadDTO();

			assignedLeadDTO.setLeadId(savedLead.getId());
			assignedLeadDTO.setFullName(savedLead.getFullName());
			assignedLeadDTO.setPhoneNumber(savedLead.getPhoneNumber());
			assignedLeadDTO.setAlternativeNumber(savedLead.getAlternativeNumber());
			assignedLeadDTO.setLocation(savedLead.getLocation());
			assignedLeadDTO.setSubLocation(savedLead.getSubLocation());
			assignedLeadDTO.setLookingFor(savedLead.getLookingFor());
			assignedLeadDTO.setTenantType(savedLead.getTenantType());
			assignedLeadDTO.setApartmentType(savedLead.getApartmentType());
			assignedLeadDTO.setAmenities(savedLead.getAmenities());
			assignedLeadDTO.setPropertyApproval(savedLead.getPropertyApproval());
			assignedLeadDTO.setPlotSize(savedLead.getPlotSize());
			assignedLeadDTO.setSuggestedPlot(savedLead.getSuggestedPlot());
			assignedLeadDTO.setRemarks(savedLead.getRemarks());
			assignedLeadDTO.setLeadStatus(savedLead.getLeadStatus());
			assignedLeadDTO.setAssignedTo(savedLead.getAssignedTo());
			assignedLeadDTO.setAssignedBy(savedLead.getAssignedBy());
			assignedLeadDTO.setDataCollector(savedLead.getDataCollector());
			assignedLeadDTO.setVisitingDate(savedLead.getVisitingDate());
			assignedLeadDTO.setVisitedDate(savedLead.getVisitedDate());
			assignedLeadDTO.setConversionDate(savedLead.getConversionDate());
			assignedLeadDTO.setFinalizedCounsellor(savedLead.getFinalizedCounsellor());
			assignedLeadDTO.setFinalizedProperty(savedLead.getFinalizedProperty());
			assignedLeadDTO.setFinalizedProof(savedLead.getFinalizedProof());
			assignedLeadDTO.setFinalizedStatus(savedLead.getFinalizedStatus());
			assignedLeadDTO.setLeadSource(savedLead.getLeadSource());
			assignedLeadDTO.setLeadFlag(savedLead.getLeadFlag());
			assignedLeadDTO.setStatus(savedLead.getStatus());
			assignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);
			assignedLeadDTO.setCreatedBy(savedLead.getCreatedBy());
			assignedLeadDTO.setCreatedDate(savedLead.getCreatedDate());

			LOGGER.info("Saving assigned lead: " + assignedLeadDTO);

			saveAssignedLead(assignedLeadDTO);
		}
	}

	private boolean isWithinLast365Days(String dateString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date createdAt = dateFormat.parse(dateString);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -365);
			Date threeHundredSixtyFiveDaysAgo = calendar.getTime();

			return createdAt.after(threeHundredSixtyFiveDaysAgo);
		} catch (ParseException e) {
			LOGGER.error("Date parsing error", e);
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> getAllLeads(LeadDTO leadDTO) {
		return leadDao.getAllLeads(leadDTO);
	}

	@Override
	public Lead getLeadById(LeadDTO leadDTO) {
		return leadDao.getLeadById(leadDTO.getId());
	}

	private void checkLead(String lead) {
		List<Lead> dbLead = leadDao.getLeadByPhone(lead);
		if (null != dbLead && dbLead.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Phone already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public void updateLead(LeadDTO leadDTO) {

		Lead lead = leadDao.getLeadById(leadDTO.getId());
		LeadDTO dbLeadDTO = LeadConverter.getLeadDTOByLead(lead);

		if (null != leadDTO.getFullName())
			dbLeadDTO.setFullName(leadDTO.getFullName());

		if (null != leadDTO.getAlternativeNumber())
			dbLeadDTO.setAlternativeNumber(leadDTO.getAlternativeNumber());

		if (null != leadDTO.getLookingFor())
			dbLeadDTO.setLookingFor(leadDTO.getLookingFor());

		if (null != leadDTO.getTenantType())
			dbLeadDTO.setTenantType(leadDTO.getTenantType());

		if (leadDTO.getLocation() != null && !leadDTO.getLocation().equals(lead.getLocation())) {
			dbLeadDTO.setLocation(leadDTO.getLocation());

			// Check if the user has the required role to assign leads
			List<Role> roles = loginService.getAllUserRoles(leadDTO.getUpdatedBy());
			boolean isLevelOneUser = roles.stream().anyMatch(role -> role.getRole().equals(RoleEnum.LEVEL_1.getRole()));

			if (isLevelOneUser) {
				// Fetch the branch of the user by createdBy ID
				String branch = userDao.getUserBranchByCreatedBy(leadDTO.getUpdatedBy());

				// Fetch all active user IDs for the new location and branch
				List<String> userIds = userDao.getUserIdsByLocationAndBranch(leadDTO.getLocation(), branch);

				if (!userIds.isEmpty()) {
					// Get the next user ID for this location using a round-robin approach
					String nextUserId = getNextUserIdForLocation(leadDTO.getLocation(), userIds);

					// Assign the lead to the next user ID
					dbLeadDTO.setAssignedTo(nextUserId);
				}
			}
		}

		if (null != leadDTO.getApartmentType())
			dbLeadDTO.setApartmentType(leadDTO.getApartmentType());

		if (null != leadDTO.getSubLocation())
			dbLeadDTO.setSubLocation(leadDTO.getSubLocation());

		if (null != leadDTO.getAmenities())
			dbLeadDTO.setAmenities(leadDTO.getAmenities());

		if (null != leadDTO.getPropertyApproval())
			dbLeadDTO.setPropertyApproval(leadDTO.getPropertyApproval());

		if (null != leadDTO.getPlotSize())
			dbLeadDTO.setPlotSize(leadDTO.getPlotSize());

		if (null != leadDTO.getSuggestedPlot())
			dbLeadDTO.setSuggestedPlot(leadDTO.getSuggestedPlot());

		if (null != leadDTO.getBudgetRange())
			dbLeadDTO.setBudgetRange(leadDTO.getBudgetRange());

		if (null != leadDTO.getCustomerOccupation())
			dbLeadDTO.setCustomerOccupation(leadDTO.getCustomerOccupation());

		if (null != leadDTO.getIncomeRange())
			dbLeadDTO.setIncomeRange(leadDTO.getIncomeRange());

		if (null != leadDTO.getPossession())
			dbLeadDTO.setPossession(leadDTO.getPossession());

		if (null != leadDTO.getRemarks())
			dbLeadDTO.setRemarks(leadDTO.getRemarks());

		if (null != leadDTO.getLeadStatus()) {
			dbLeadDTO.setLeadStatus(leadDTO.getLeadStatus());
			updateLeadStatus(leadDTO);
		}

		if (null != leadDTO.getDataCollector())
			dbLeadDTO.setDataCollector(leadDTO.getDataCollector());

		if (null != leadDTO.getVisitingDate())
			dbLeadDTO.setVisitingDate(leadDTO.getVisitingDate());

		if (null != leadDTO.getVisitedDate())
			dbLeadDTO.setVisitedDate(leadDTO.getVisitedDate());

		if (null != leadDTO.getConversionDate())
			dbLeadDTO.setConversionDate(leadDTO.getConversionDate());

		if (null != leadDTO.getFinalizedCounsellor())
			dbLeadDTO.setFinalizedCounsellor(leadDTO.getFinalizedCounsellor());

		if (null != leadDTO.getFinalizedProperty())
			dbLeadDTO.setFinalizedProperty(leadDTO.getFinalizedProperty());

		if (null != leadDTO.getFinalizedProof())
			dbLeadDTO.setFinalizedProof(leadDTO.getFinalizedProof());

		if (null != leadDTO.getFinalizedStatus())
			dbLeadDTO.setFinalizedStatus(leadDTO.getFinalizedStatus());

		if (null != leadDTO.getAssignedTo())
			dbLeadDTO.setAssignedTo(leadDTO.getAssignedTo());

		dbLeadDTO.setUpdatedBy(leadDTO.getUpdatedBy());
		dbLeadDTO.setUpdatedDate(leadDTO.getUpdatedDate());

		// Update the lead
		leadDao.saveLead(dbLeadDTO);
		LOGGER.info("Lead " + leadDTO.getId() + " updated successfully by " + leadDTO.getUpdatedBy());
	}

	private void updateLeadStatus(LeadDTO leadDTO) {
		if (leadDTO.getLeadStatus() != null) {
			switch (leadDTO.getLeadStatus().toLowerCase()) {
			case "followup":
				handleAssignedLead(leadDTO);
				break;
			case "call-immediately":
				handleAssignedLead(leadDTO);
				break;
			default:
				LOGGER.warn("Unhandled lead status: " + leadDTO.getLeadStatus());
				break;
			}
		} else {
			LOGGER.warn("Lead status is null for lead ID: " + leadDTO.getId());
		}
	}

	private void handleAssignedLead(LeadDTO leadDTO) {
		Lead lead = leadDao.getLeadById(leadDTO.getId());

		// Create a Set to track assigned user IDs
		Set<String> assignedUserIds = new HashSet<>();

		// Create the initial AssignedLeadDTO with all data from the lead
		AssignedLeadDTO initialAssignedLeadDTO = new AssignedLeadDTO();
		initialAssignedLeadDTO.setLeadId(lead.getId());
		initialAssignedLeadDTO.setFullName(lead.getFullName());
		initialAssignedLeadDTO.setPhoneNumber(lead.getPhoneNumber());
		initialAssignedLeadDTO.setAlternativeNumber(lead.getAlternativeNumber());
		initialAssignedLeadDTO.setLocation(lead.getLocation());
		initialAssignedLeadDTO.setSubLocation(lead.getSubLocation());
		initialAssignedLeadDTO.setLookingFor(lead.getLookingFor());
		initialAssignedLeadDTO.setTenantType(lead.getTenantType());
		initialAssignedLeadDTO.setApartmentType(lead.getApartmentType());
		initialAssignedLeadDTO.setAmenities(lead.getAmenities());
		initialAssignedLeadDTO.setPropertyApproval(lead.getPropertyApproval());
		initialAssignedLeadDTO.setPlotSize(lead.getPlotSize());
		initialAssignedLeadDTO.setSuggestedPlot(lead.getSuggestedPlot());
		initialAssignedLeadDTO.setBudgetRange(lead.getBudgetRange());
		initialAssignedLeadDTO.setCustomerOccupation(lead.getCustomerOccupation());
		initialAssignedLeadDTO.setIncomeRange(lead.getIncomeRange());
		initialAssignedLeadDTO.setPossession(lead.getPossession());
		initialAssignedLeadDTO.setRemarks(lead.getRemarks());
		initialAssignedLeadDTO.setLeadStatus("Assigned");
		initialAssignedLeadDTO.setAssignedTo(lead.getAssignedTo());
		initialAssignedLeadDTO.setAssignedBy(lead.getAssignedBy());
		initialAssignedLeadDTO.setDataCollector(lead.getDataCollector());
		initialAssignedLeadDTO.setVisitingDate(lead.getVisitingDate());
		initialAssignedLeadDTO.setVisitedDate(lead.getVisitedDate());
		initialAssignedLeadDTO.setConversionDate(lead.getConversionDate());
		initialAssignedLeadDTO.setFinalizedCounsellor(lead.getFinalizedCounsellor());
		initialAssignedLeadDTO.setFinalizedProperty(lead.getFinalizedProperty());
		initialAssignedLeadDTO.setFinalizedProof(lead.getFinalizedProof());
		initialAssignedLeadDTO.setFinalizedStatus(lead.getFinalizedStatus());
		initialAssignedLeadDTO.setLeadSource(lead.getLeadSource());
		initialAssignedLeadDTO.setLeadFlag(lead.getLeadFlag());
		initialAssignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);
		initialAssignedLeadDTO.setCreatedBy(leadDTO.getUpdatedBy());
		initialAssignedLeadDTO.setCreatedDate(leadDTO.getUpdatedDate());

		// Save the initial assignment if the user has not been assigned yet
		if (assignedUserIds.add(lead.getAssignedTo())) {
			List<String> existingAssignments = assignedLeadDao.existsByLeadIdAndAssignedTo(lead.getId(),
					Long.parseLong(lead.getAssignedTo()));
			if (existingAssignments.isEmpty()) {
				saveAssignedLead(initialAssignedLeadDTO);
			}
		}

		// Split the collegeSecond field by "/" and trim any leading/trailing spaces
		String[] lookingfors = lead.getLookingFor().split("/");
		for (String lookingFor : lookingfors) {
			lookingFor = lookingFor.trim();

			// Retrieve the user's branch
			String branch = userDao.getUserBranchByCreatedBy(leadDTO.getUpdatedBy());

			// Check if the college value indicates a special branch or office visit
			if (lookingFor.equalsIgnoreCase("office visit")) {
				// Handle the office visit case: assign to users from the user's branch
				List<String> branchUserIds = userDao.getUserIdsByBranchAndAssignedAsset(branch, lookingFor);

				if (!branchUserIds.isEmpty()) {
					// Create and save an AssignedLeadDTO for each user in the branch
					for (String userId : branchUserIds) {
						// Save only if the user has not been assigned yet
						if (assignedUserIds.add(userId)) {
							List<String> existingAssignments = assignedLeadDao.existsByLeadIdAndAssignedTo(lead.getId(),
									Long.parseLong(userId));
							if (existingAssignments.isEmpty()) {
								AssignedLeadDTO assignedLeadDTO = new AssignedLeadDTO();

								assignedLeadDTO.setLeadId(lead.getId());
								assignedLeadDTO.setFullName(lead.getFullName());
								assignedLeadDTO.setPhoneNumber(lead.getPhoneNumber());
								assignedLeadDTO.setAlternativeNumber(lead.getAlternativeNumber());
								assignedLeadDTO.setLocation(lead.getLocation());
								assignedLeadDTO.setSubLocation(lead.getSubLocation());
								assignedLeadDTO.setLookingFor(lead.getLookingFor());
								assignedLeadDTO.setTenantType(lead.getTenantType());
								assignedLeadDTO.setApartmentType(lead.getApartmentType());
								assignedLeadDTO.setAmenities(lead.getAmenities());
								assignedLeadDTO.setPropertyApproval(lead.getPropertyApproval());
								assignedLeadDTO.setPlotSize(lead.getPlotSize());
								assignedLeadDTO.setSuggestedPlot(lead.getSuggestedPlot());
								assignedLeadDTO.setRemarks(lead.getRemarks());
								assignedLeadDTO.setLeadStatus("AssetData");
								assignedLeadDTO.setAssignedTo(userId);
								assignedLeadDTO.setAssignedBy(lead.getAssignedBy());
								assignedLeadDTO.setDataCollector(lead.getDataCollector());
								assignedLeadDTO.setVisitingDate(lead.getVisitingDate());
								assignedLeadDTO.setVisitedDate(lead.getVisitedDate());
								assignedLeadDTO.setConversionDate(lead.getConversionDate());
								assignedLeadDTO.setFinalizedCounsellor(lead.getFinalizedCounsellor());
								assignedLeadDTO.setFinalizedProperty(lead.getFinalizedProperty());
								assignedLeadDTO.setFinalizedProof(lead.getFinalizedProof());
								assignedLeadDTO.setFinalizedStatus(lead.getFinalizedStatus());
								assignedLeadDTO.setPossession(lead.getPossession());
								assignedLeadDTO.setLeadSource(lead.getLeadSource());
								assignedLeadDTO.setLeadFlag(lead.getLeadFlag());
								assignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);
								assignedLeadDTO.setCreatedBy(leadDTO.getUpdatedBy());
								assignedLeadDTO.setCreatedDate(leadDTO.getUpdatedDate());

								saveAssignedLead(assignedLeadDTO);
							}
						}
					}
				}
			} else {
				// Handle the case for specific colleges
				List<String> userIds = userDao.getUserIdsByAssignedAsset(lookingFor);

				if (!userIds.isEmpty()) {
					// Create and save an AssignedLeadDTO for each user associated with the college
					for (String userId : userIds) {
						// Save only if the user has not been assigned yet
						if (assignedUserIds.add(userId)) {
							List<String> existingAssignments = assignedLeadDao.existsByLeadIdAndAssignedTo(lead.getId(),
									Long.parseLong(userId));
							if (existingAssignments.isEmpty()) {
								AssignedLeadDTO assignedLeadDTO = new AssignedLeadDTO();
								assignedLeadDTO.setLeadId(lead.getId());
								assignedLeadDTO.setFullName(lead.getFullName());
								assignedLeadDTO.setPhoneNumber(lead.getPhoneNumber());
								assignedLeadDTO.setAlternativeNumber(lead.getAlternativeNumber());
								assignedLeadDTO.setLocation(lead.getLocation());
								assignedLeadDTO.setSubLocation(lead.getSubLocation());
								assignedLeadDTO.setLookingFor(lead.getLookingFor());
								assignedLeadDTO.setTenantType(lead.getTenantType());
								assignedLeadDTO.setApartmentType(lead.getApartmentType());
								assignedLeadDTO.setAmenities(lead.getAmenities());
								assignedLeadDTO.setPropertyApproval(lead.getPropertyApproval());
								assignedLeadDTO.setPlotSize(lead.getPlotSize());
								assignedLeadDTO.setSuggestedPlot(lead.getSuggestedPlot());
								assignedLeadDTO.setRemarks(lead.getRemarks());
								assignedLeadDTO.setLeadStatus("AssetData");
								assignedLeadDTO.setAssignedTo(userId);
								assignedLeadDTO.setAssignedBy(lead.getAssignedBy());
								assignedLeadDTO.setDataCollector(lead.getDataCollector());
								assignedLeadDTO.setVisitingDate(lead.getVisitingDate());
								assignedLeadDTO.setVisitedDate(lead.getVisitedDate());
								assignedLeadDTO.setConversionDate(lead.getConversionDate());
								assignedLeadDTO.setFinalizedCounsellor(lead.getFinalizedCounsellor());
								assignedLeadDTO.setFinalizedProperty(lead.getFinalizedProperty());
								assignedLeadDTO.setFinalizedProof(lead.getFinalizedProof());
								assignedLeadDTO.setFinalizedStatus(lead.getFinalizedStatus());
								assignedLeadDTO.setLeadSource(lead.getLeadSource());
								assignedLeadDTO.setPossession(lead.getPossession());
								assignedLeadDTO.setLeadFlag(lead.getLeadFlag());
								assignedLeadDTO.setStatus(Constant.STATUS_ACTIVE);
								assignedLeadDTO.setCreatedBy(leadDTO.getUpdatedBy());
								assignedLeadDTO.setCreatedDate(leadDTO.getUpdatedDate());

								saveAssignedLead(assignedLeadDTO);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void saveAssignedLead(AssignedLeadDTO assignedLeadDTO) {
		assignedLeadDao.saveAssignedLead(assignedLeadDTO);
		LOGGER.info("Assigned lead added successfully for lead ID: " + assignedLeadDTO.getLeadId() + " by "
				+ assignedLeadDTO.getCreatedBy());
	}

	@Override
	public void assignLeads(LeadDTO leadDTO) {
		// Step 2: Get list of leads
		List<Lead> leadsToAssign = leadDao.getAllLead(leadDTO);
		if (leadsToAssign == null || leadsToAssign.isEmpty()) {
			throw new FieldException("Leads are not present in the system");
		}

		List<Long> existIds = leadsToAssign.stream().map(Lead::getId).collect(Collectors.toList());
//		if (!existIds.containsAll(leadDTO.getLeads())) {
//			throw new FieldException("Some of the given leadIds are not present in the database");
//		}

		// Step 3: Update the leads' assignedBy and assignedTo fields
		for (Lead lead : leadsToAssign) {
			LeadDTO dbLead = LeadConverter.getLeadDTOByLead(lead);

			// Check if given assignTo is equal to db assignTo
			if (dbLead.getAssignedTo() != null && dbLead.getAssignedTo().equals(leadDTO.getAssignedTo())) {
				LOGGER.info("Same userId " + leadDTO.getAssignedTo() + " assigning to lead " + dbLead.getId()
						+ " so we are not updating lead again");
				continue;
			}

			// Update lead fields
			dbLead.setAssignedBy(leadDTO.getUpdatedBy());
			dbLead.setAssignedTo(leadDTO.getAssignedTo());
			dbLead.setUpdatedBy(leadDTO.getUpdatedBy());
			dbLead.setUpdatedDate(leadDTO.getUpdatedDate());

			// Save updated lead
			leadDao.saveLead(dbLead);

			LOGGER.info("Lead " + dbLead.getId() + " assigned to " + leadDTO.getAssignedTo() + " successfully by "
					+ leadDTO.getUpdatedBy());
		}
	}

	private synchronized String getNextUserIdForLocation(String location, List<String> userIds) {
		int index = lastAssignedUserIndex.getOrDefault(location, -1);
		index = (index + 1) % userIds.size();
		lastAssignedUserIndex.put(location, index);
		return userIds.get(index);
	}

	@Override
	public void updateAssignLead(LeadDTO leadDTO) {

		Lead lead = leadDao.getLeadById(leadDTO.getId());
		LeadDTO dbLeadDTO = LeadConverter.getLeadDTOByLead(lead);

		dbLeadDTO.setAssignedTo(leadDTO.getAssignedTo());
		dbLeadDTO.setAssignedBy(leadDTO.getUpdatedBy());

		leadDao.saveLead(dbLeadDTO);
		LOGGER.info("Lead " + leadDTO.getId() + " updated successfully by " + leadDTO.getUpdatedBy());

	}

	// update visited leads
	@Override
	public void saveVisitedLead(VisitedLeadsDTO visitedLeadsDTO) {
		visitedLeadsDao.saveVisitedLead(visitedLeadsDTO);
		LOGGER.info("Visited Lead added successfully by " + visitedLeadsDTO.getCreatedBy());
	}

	@Override
	public List<Map<String, Object>> getVisitedLeads(VisitedLeadsDTO visitedLeadsDTO) {
		return visitedLeadsDao.getVisitedLeads(visitedLeadsDTO);
	}

	@Override
	public List<Map<String, Object>> getAllAssignedLeads(AssignedLeadDTO assignedLeadDTO) {
		return assignedLeadDao.getAllAssignedLeads(assignedLeadDTO);
	}

	@Override
	public List<Map<String, Object>> getAllLevelThreeLeads(AssignedLeadDTO assignedLeadDTO) {
		return assignedLeadDao.getAllLevelThreeLeads(assignedLeadDTO);
	}

	@Override
	public AssignedLead getAssignedLeadById(AssignedLeadDTO assignedLeadDTO) {
		return assignedLeadDao.getAssignedLeadById(assignedLeadDTO.getId());
	}

	@Override
	public void updateAssignedLead(AssignedLeadDTO assignedLeadDTO) {

		AssignedLead assignedLead = assignedLeadDao.getAssignedLeadById(assignedLeadDTO.getId());
		AssignedLeadDTO dbLeadDTO = AssignedLeadConverter.getAssignedLeadDTOByAssignedLead(assignedLead);

		if (null != assignedLeadDTO.getFullName())
			dbLeadDTO.setFullName(assignedLeadDTO.getFullName());

		if (null != assignedLeadDTO.getAlternativeNumber())
			dbLeadDTO.setAlternativeNumber(assignedLeadDTO.getAlternativeNumber());

		if (null != assignedLeadDTO.getLocation())
			dbLeadDTO.setLocation(assignedLeadDTO.getLocation());

		if (null != assignedLeadDTO.getSubLocation())
			dbLeadDTO.setSubLocation(assignedLeadDTO.getSubLocation());

		if (null != assignedLeadDTO.getLookingFor())
			dbLeadDTO.setLookingFor(assignedLeadDTO.getLookingFor());

		if (null != assignedLeadDTO.getTenantType())
			dbLeadDTO.setTenantType(assignedLeadDTO.getTenantType());

		if (null != assignedLeadDTO.getApartmentType())
			dbLeadDTO.setApartmentType(assignedLeadDTO.getApartmentType());

		if (null != assignedLeadDTO.getAmenities())
			dbLeadDTO.setAmenities(assignedLeadDTO.getAmenities());

		if (null != assignedLeadDTO.getPropertyApproval())
			dbLeadDTO.setPropertyApproval(assignedLeadDTO.getPropertyApproval());

		if (null != assignedLeadDTO.getPlotSize())
			dbLeadDTO.setPlotSize(assignedLeadDTO.getPlotSize());

		if (null != assignedLeadDTO.getSuggestedPlot())
			dbLeadDTO.setSuggestedPlot(assignedLeadDTO.getSuggestedPlot());

		if (null != assignedLeadDTO.getBudgetRange())
			dbLeadDTO.setBudgetRange(assignedLeadDTO.getBudgetRange());

		if (null != assignedLeadDTO.getCustomerOccupation())
			dbLeadDTO.setCustomerOccupation(assignedLeadDTO.getCustomerOccupation());

		if (null != assignedLeadDTO.getIncomeRange())
			dbLeadDTO.setIncomeRange(assignedLeadDTO.getIncomeRange());

		if (null != assignedLeadDTO.getPossession())
			dbLeadDTO.setPossession(assignedLeadDTO.getPossession());

		if (null != assignedLeadDTO.getRemarks())
			dbLeadDTO.setRemarks(assignedLeadDTO.getRemarks());

		if (null != assignedLeadDTO.getVisitingDate())
			dbLeadDTO.setVisitingDate(assignedLeadDTO.getVisitingDate());

		if (null != assignedLeadDTO.getVisitedDate())
			dbLeadDTO.setVisitedDate(assignedLeadDTO.getVisitedDate());

		if (null != assignedLeadDTO.getConversionDate())
			dbLeadDTO.setConversionDate(assignedLeadDTO.getConversionDate());

		if (null != assignedLeadDTO.getFinalizedCounsellor())
			dbLeadDTO.setFinalizedCounsellor(assignedLeadDTO.getFinalizedCounsellor());

		if (null != assignedLeadDTO.getFinalizedProperty())
			dbLeadDTO.setFinalizedProperty(assignedLeadDTO.getFinalizedProperty());

		if (null != assignedLeadDTO.getFinalizedProof())
			dbLeadDTO.setFinalizedProof(assignedLeadDTO.getFinalizedProof());

		if (null != assignedLeadDTO.getFinalizedStatus())
			dbLeadDTO.setFinalizedStatus(assignedLeadDTO.getFinalizedStatus());

		if (null != assignedLeadDTO.getAssignedTo())
			dbLeadDTO.setAssignedTo(assignedLeadDTO.getAssignedTo());

		if (null != assignedLeadDTO.getAssignedBy())
			dbLeadDTO.setAssignedBy(assignedLeadDTO.getAssignedBy());

		if (null != assignedLeadDTO.getFinalizedStatus())
			dbLeadDTO.setFinalizedStatus(assignedLeadDTO.getFinalizedStatus());

		if (null != assignedLeadDTO.getLeadStatus()) {
			dbLeadDTO.setLeadStatus(assignedLeadDTO.getLeadStatus());
			updateAssignedLeadStatus(assignedLeadDTO);
		}

		dbLeadDTO.setUpdatedBy(assignedLeadDTO.getUpdatedBy());
		dbLeadDTO.setUpdatedDate(assignedLeadDTO.getUpdatedDate());

		// Update the lead
		assignedLeadDao.saveAssignedLead(dbLeadDTO);
		LOGGER.info("Assigned Lead " + assignedLeadDTO.getId() + " updated successfully by "
				+ assignedLeadDTO.getUpdatedBy());
	}

	private void updateAssignedLeadStatus(AssignedLeadDTO assignedLeadDTO) {
		if (assignedLeadDTO.getLeadStatus() != null) {
			switch (assignedLeadDTO.getLeadStatus().toLowerCase()) {
			case "visited":
				handleVisitedLead(assignedLeadDTO);
				break;
			case "purchased":
				handleAdmittedLead(assignedLeadDTO);
				break;
			default:
				LOGGER.warn("Unhandled lead status: " + assignedLeadDTO.getLeadStatus());
				break;
			}
		} else {
			LOGGER.warn("Lead status is null for lead ID: " + assignedLeadDTO.getId());
		}
	}

	private void handleVisitedLead(AssignedLeadDTO assignedLeadDTO) {

		VisitedLeadsDTO visitedLeadsDTO = new VisitedLeadsDTO();
		visitedLeadsDTO.setLeadId(assignedLeadDTO.getLeadId());
		visitedLeadsDTO.setProperty(assignedLeadDTO.getSuggestedPlot());
		visitedLeadsDTO.setVisitedDate(assignedLeadDTO.getVisitedDate());
		visitedLeadsDTO.setPropertyVisitCounsellor(assignedLeadDTO.getFinalizedCounsellor());
		visitedLeadsDTO.setRemarks(assignedLeadDTO.getRemarks());
		visitedLeadsDTO.setStatus(Constant.STATUS_ACTIVE);
		visitedLeadsDTO.setCreatedBy(assignedLeadDTO.getUpdatedBy());
		visitedLeadsDTO.setCreatedDate(assignedLeadDTO.getUpdatedDate());
		saveVisitedLead(visitedLeadsDTO);
	}

	private void handleAdmittedLead(AssignedLeadDTO assignedLeadDTO) {
		AssignedLead assignedLead = assignedLeadDao.getAssignedLeadById(assignedLeadDTO.getId());

		if (assignedLead == null || CustomValidator.isEmpty(assignedLead.getLeadId())) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM001");
			errorMap.put(Constant.RESPONSE_MSG_KEY,
					"LeadId Is Mandatory for purchased and must be retrieved from the database");
			throw new InterruptExitException(errorMap);
		}

		// Delete from assigned leads
		List<AssignedLead> existingAssignedLeads = assignedLeadDao.getAssignedLeadsById(assignedLead.getLeadId());
		if (existingAssignedLeads != null) {
			for (AssignedLead existingAssignedLead : existingAssignedLeads) {
				existingAssignedLead.setStatus(Constant.STATUS_INACTIVE);
				saveAssignedLead(existingAssignedLead);
			}
		}

		// Update the retrieved assigned lead
		AssignedLeadDTO dbLeadDTO = AssignedLeadConverter.getAssignedLeadDTOByAssignedLead(assignedLead);
		dbLeadDTO.setStatus(Constant.STATUS_ACTIVE);
		dbLeadDTO.setUpdatedBy(assignedLeadDTO.getUpdatedBy());
		dbLeadDTO.setUpdatedDate(assignedLeadDTO.getUpdatedDate());

		assignedLeadDao.saveAssignedLead(dbLeadDTO);
		LOGGER.info("Assigned Lead " + assignedLeadDTO.getId() + " updated successfully by "
				+ assignedLeadDTO.getUpdatedBy());
	}

	private void saveAssignedLead(AssignedLead assignedLead) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Map<String, Object>> getAllLeadsCount(LeadDTO leadDTO) {
		return leadDao.getAllLeadsCount(leadDTO);
	}

	@Override
	public void saveNewAssignedLead(AssignedLeadDTO assignedLeadDTO) {
		saveAssignedLead(assignedLeadDTO);
		LOGGER.info("Lead added successfully by " + assignedLeadDTO.getCreatedBy());
	}
}