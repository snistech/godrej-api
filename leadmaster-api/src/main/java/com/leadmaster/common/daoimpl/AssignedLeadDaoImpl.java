package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.AssignedLeadConverter;
import com.leadmaster.common.dao.AssignedLeadDao;
import com.leadmaster.common.domain.AssignedLead;
import com.leadmaster.common.dto.AssignedLeadDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.AssignedLeadRepository;

@Transactional
@Service("AssignedLeadDaoImpl")
public class AssignedLeadDaoImpl implements AssignedLeadDao {

	private Logger LOGGER = LoggerFactory.getLogger(LeadDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AssignedLeadRepository assignedLeadRepository;

	@Override
	public AssignedLead saveAssignedLead(AssignedLeadDTO assignedLeadDTO) {
		AssignedLead assignedLead = AssignedLeadConverter.getAssignedLeadByAssignedLeadDTO(assignedLeadDTO);
		return assignedLeadRepository.save(assignedLead);

	}

	@Override
	public AssignedLead getAssignedLeadById(Long id) {
		Optional<AssignedLead> assignedLead = assignedLeadRepository.findById(id);
		if (!assignedLead.isPresent())
			throw new ResourceNotFoundException("The Assigned Lead is not found in the system. id:" + id);
		return assignedLead.get();
	}

	@Override
	public List<Map<String, Object>> getAllAssignedLeads(AssignedLeadDTO assignedLeadDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT a.id, a.full_name, a.lead_id, a.phone_number, a.alternative_number, "
						+ "a.location, a.looking_for, a.tenant_type, a.apartment_type, a.amenities, "
						+ "a.property_approval, a.plot_size, a.assigned_to, a.assigned_by, a.updated_date, "
						+ "a.visiting_date, a.visited_date, a.conversion_date, a.finalized_counsellor, "
						+ "a.finalized_status, a.suggested_plot, a.finalized_property, a.finalized_proof, "
						+ "a.remarks, a.lead_status, a.budget_range, a.customer_occupation, a.possession, "
						+ "a.sub_location, a.created_date, a.lead_source, a.lead_flag " + "FROM assigned_leads a "
						+ "INNER JOIN users u ON a.assigned_to = u.id " + "WHERE 1=1");

		// Append filters based on leadDTOpossessionpossession
		if (assignedLeadDTO.getId() != null)
			sqlQuery.append(" AND a.id = :id");
		if (assignedLeadDTO.getFullName() != null)
			sqlQuery.append(" AND a.full_name = :fullName");
		if (assignedLeadDTO.getPhoneNumber() != null)
			sqlQuery.append(" AND a.phone_number = :phoneNumber");
		if (assignedLeadDTO.getDataCollector() != null)
			sqlQuery.append(" AND a.data_collector = :dataCollector");
		if (assignedLeadDTO.getSubLocation() != null)
			sqlQuery.append(" AND a.sub_location = :subLocation");
		if (assignedLeadDTO.getLocation() != null)
			sqlQuery.append(" AND a.location = :location");
		if (assignedLeadDTO.getFinalizedProperty() != null && !assignedLeadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedProperties = assignedLeadDTO.getFinalizedProperty().split("\\s*/\\s*");
			sqlQuery.append(" AND (");
			for (int i = 0; i < finalizedProperties.length; i++) {
				if (i > 0)
					sqlQuery.append(" OR ");
				sqlQuery.append("a.finalized_property LIKE :finalizedProperty").append(i);
			}
			sqlQuery.append(")");
		}
		if (assignedLeadDTO.getRemarks() != null)
			sqlQuery.append(" AND a.remarks = :remarks");
		if (assignedLeadDTO.getLeadStatus() != null && !assignedLeadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = assignedLeadDTO.getLeadStatus().split(",\\s*");
			sqlQuery.append(" AND a.lead_status IN (:statuses)");
		}
		if (assignedLeadDTO.getFinalizedStatus() != null && !assignedLeadDTO.getFinalizedStatus().isEmpty()) {
			String[] admStatuses = assignedLeadDTO.getFinalizedStatus().split(",\\s*");
			sqlQuery.append(" AND a.finalized_status IN (:finalizedStatuses)");
		}
		if (assignedLeadDTO.getVisitingDate() != null)
			sqlQuery.append(" AND DATE(a.visiting_date) = :visitingDate");
		if (assignedLeadDTO.getVisitedDate() != null)
			sqlQuery.append(" AND DATE(a.visited_date) = :visitedDate");
		if (assignedLeadDTO.getConversionDate() != null)
			sqlQuery.append(" AND DATE(a.conversion_date) = :conversionDate");
		if (assignedLeadDTO.getFinalizedCounsellor() != null)
			sqlQuery.append(" AND a.finalized_counsellor = :finalizedCounsellor");
		if (assignedLeadDTO.getAssignedTo() != null) {
			String[] assignedToValues = assignedLeadDTO.getAssignedTo().split(",\\s*");
			sqlQuery.append(" AND (");
			for (int i = 0; i < assignedToValues.length; i++) {
				if (i > 0)
					sqlQuery.append(" OR ");
				sqlQuery.append("a.assigned_to LIKE CONCAT('%,', :assignedTo").append(i).append(", ',%') ")
						.append("OR a.assigned_to LIKE CONCAT(:assignedTo").append(i).append(", ',%') ")
						.append("OR a.assigned_to LIKE CONCAT('%,', :assignedTo").append(i).append(") ")
						.append("OR a.assigned_to = :assignedTo").append(i);
			}
			sqlQuery.append(")");
		}
		if (assignedLeadDTO.getStartDate() != null && assignedLeadDTO.getEndDate() != null) {
			sqlQuery.append(" AND DATE(a.updated_date) BETWEEN :startDate AND :endDate");
		} else if (assignedLeadDTO.getStartDate() != null) {
			sqlQuery.append(" AND DATE(a.updated_date) = :startDate");
		}
//		if (assignedLeadDTO.getBranch() != null)
//			sqlQuery.append(" AND u.branch = :branch");
		if (assignedLeadDTO.getSuggestedPlot() != null)
			sqlQuery.append(" AND a.suggested_plot = :suggestedPlot");
		if (assignedLeadDTO.getTeamLead() != null)
			sqlQuery.append(" AND u.team_lead = :teamLead");
		if (assignedLeadDTO.getMarketingExecutive() != null)
			sqlQuery.append(" AND u.marketing_executive = :marketingExecutive");

		sqlQuery.append(" ORDER BY a.id DESC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		// Set parameters
		if (assignedLeadDTO.getId() != null)
			query.setParameter("id", assignedLeadDTO.getId());
		if (assignedLeadDTO.getFullName() != null)
			query.setParameter("fullName", assignedLeadDTO.getFullName());
		if (assignedLeadDTO.getPhoneNumber() != null)
			query.setParameter("phoneNumber", assignedLeadDTO.getPhoneNumber());
		if (assignedLeadDTO.getDataCollector() != null)
			query.setParameter("dataCollector", assignedLeadDTO.getDataCollector());
		if (assignedLeadDTO.getSubLocation() != null)
			query.setParameter("subLocation", assignedLeadDTO.getSubLocation());
		if (assignedLeadDTO.getLocation() != null)
			query.setParameter("location", assignedLeadDTO.getLocation());

		if (assignedLeadDTO.getFinalizedProperty() != null && !assignedLeadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedProperties = assignedLeadDTO.getFinalizedProperty().split("\\s*/\\s*");
			for (int i = 0; i < finalizedProperties.length; i++) {
				query.setParameter("finalizedProperty" + i, "%" + finalizedProperties[i].trim() + "%");
			}
		}
		if (assignedLeadDTO.getRemarks() != null)
			query.setParameter("remarks", assignedLeadDTO.getRemarks());
		if (assignedLeadDTO.getLeadStatus() != null && !assignedLeadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = assignedLeadDTO.getLeadStatus().split(",\\s*");
			query.setParameter("statuses", Arrays.asList(statuses));
		}
		if (assignedLeadDTO.getFinalizedStatus() != null && !assignedLeadDTO.getFinalizedStatus().isEmpty()) {
			String[] finalizedStatuses = assignedLeadDTO.getFinalizedStatus().split(",\\s*");
			query.setParameter("finalizedStatuses", Arrays.asList(finalizedStatuses));
		}
		if (assignedLeadDTO.getVisitingDate() != null)
			query.setParameter("visitingDate", assignedLeadDTO.getVisitingDate());
		if (assignedLeadDTO.getVisitedDate() != null)
			query.setParameter("visitedDate", assignedLeadDTO.getVisitedDate());
		if (assignedLeadDTO.getConversionDate() != null)
			query.setParameter("conversionDate", assignedLeadDTO.getConversionDate());
		if (assignedLeadDTO.getFinalizedCounsellor() != null)
			query.setParameter("finalizedCounsellor", assignedLeadDTO.getFinalizedCounsellor());
		if (assignedLeadDTO.getAssignedTo() != null) {
			String[] assignedToValues = assignedLeadDTO.getAssignedTo().split(",\\s*");
			for (int i = 0; i < assignedToValues.length; i++) {
				query.setParameter("assignedTo" + i, assignedToValues[i]);
			}
		}
		if (assignedLeadDTO.getStartDate() != null && assignedLeadDTO.getEndDate() != null) {
			query.setParameter("startDate", assignedLeadDTO.getStartDate());
			query.setParameter("endDate", assignedLeadDTO.getEndDate());
		} else if (assignedLeadDTO.getStartDate() != null) {
			query.setParameter("startDate", assignedLeadDTO.getStartDate());
		}
//		if (assignedLeadDTO.getBranch() != null)
//			query.setParameter("branch", assignedLeadDTO.getBranch());
		if (assignedLeadDTO.getSuggestedPlot() != null)
			query.setParameter("suggestedPlot", assignedLeadDTO.getSuggestedPlot());
		if (assignedLeadDTO.getTeamLead() != null)
			query.setParameter("teamLead", assignedLeadDTO.getTeamLead());
		if (assignedLeadDTO.getMarketingExecutive() != null)
			query.setParameter("marketingExecutive", assignedLeadDTO.getMarketingExecutive());

		// Apply pagination
		query.setFirstResult(assignedLeadDTO.getOffset());
		query.setMaxResults(assignedLeadDTO.getLimit());

		List<Object[]> resultList = query.getResultList();

		// Map results to List of Maps
		for (Object[] result : resultList) {
			Map<String, Object> leadMap = new LinkedHashMap<>();
			leadMap.put("id", result[0]);
			leadMap.put("fullName", result[1]);
			leadMap.put("leadId", result[2]);
			leadMap.put("phoneNumber", result[3]);
			leadMap.put("alternativeNumber", result[4]);
			leadMap.put("location", result[5]);
			leadMap.put("lookingFor", result[6]);
			leadMap.put("tenantType", result[7]);
			leadMap.put("apartmentType", result[8]);
			leadMap.put("amenities", result[9]);
			leadMap.put("propertyApproval", result[10]);
			leadMap.put("plotSize", result[11]);
			leadMap.put("assignedTo", result[12]);
			leadMap.put("assignedBy", result[13]);
			leadMap.put("updatedDate", result[14]);
			leadMap.put("visitingDate", result[15]);
			leadMap.put("visitedDate", result[16]);
			leadMap.put("conversionDate", result[17]);
			leadMap.put("finalizedCounsellor", result[18]);
			leadMap.put("finalizedStatus", result[19]);
			leadMap.put("suggestedPlot", result[20]);
			leadMap.put("finalizedProperty", result[21]);
			leadMap.put("finalizedProof", result[22]);
			leadMap.put("remarks", result[23]);
			leadMap.put("leadStatus", result[24]);
			leadMap.put("budgetRange", result[25]);
			leadMap.put("customerOccupation", result[26]);
			leadMap.put("possession", result[27]);
			leadMap.put("subLocation", result[28]);
			leadMap.put("createdDate", result[29]);
			leadMap.put("leadSource", result[30]);
			leadMap.put("leadFlag", result[31]);

			returnList.add(leadMap);
		}
		return returnList;
	}

	@Override
	public List<String> getLeadIdsByLeadId(Long leadId) {
		return assignedLeadRepository.getLeadIdsByLeadId(leadId);
	}

	@Override
	public void deleteLeadsByLeadId(Long leadId) {
		assignedLeadRepository.deleteLeadsByLeadId(leadId);
	}

	@Override
	public List<AssignedLead> getAssignedLeadsById(Long leadId) {
		return assignedLeadRepository.getAssignedLeadsById(leadId);
	}

	@Override
	public List<String> existsByLeadIdAndAssignedTo(Long leadId, Long assignedTo) {
		return assignedLeadRepository.existsByLeadIdAndAssignedTo(leadId, assignedTo);
	}

	@Override
	public List<Map<String, Object>> getAllLevelThreeLeads(AssignedLeadDTO assignedLeadDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder("SELECT a.id, a.lead_id, a.full_name, a.phone_number, a.lead_status "
				+ "FROM assigned_leads a " + "INNER JOIN users u ON a.assigned_to = u.id WHERE 1=1");

		// Append filters based on leadDTO
		if (assignedLeadDTO.getId() != null)
			sqlQuery.append(" AND a.id = :id");
		if (assignedLeadDTO.getFullName() != null)
			sqlQuery.append(" AND a.full_name = :fullName");
		if (assignedLeadDTO.getPhoneNumber() != null)
			sqlQuery.append(" AND a.phone_number = :phoneNumber");
		if (assignedLeadDTO.getLocation() != null)
			sqlQuery.append(" AND a.location = :location");
		if (assignedLeadDTO.getSubLocation() != null)
			sqlQuery.append(" AND a.sub_location = :subLocation");
		if (assignedLeadDTO.getDataCollector() != null)
			sqlQuery.append(" AND a.data_collector = :dataCollector");
		if (assignedLeadDTO.getFinalizedProperty() != null && !assignedLeadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedPropertyArray = assignedLeadDTO.getFinalizedProperty().split("\\s*/\\s*");
			sqlQuery.append(" AND (");
			for (int i = 0; i < finalizedPropertyArray.length; i++) {
				if (i > 0) {
					sqlQuery.append(" OR ");
				}
				sqlQuery.append("a.finalized_property LIKE :finalizedProperty").append(i);
			}
			sqlQuery.append(")");
		}
		if (assignedLeadDTO.getLeadStatus() != null && !assignedLeadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = assignedLeadDTO.getLeadStatus().split(",\\s*");
			sqlQuery.append(" AND a.lead_status IN (:statuses)");
		}
		if (assignedLeadDTO.getFinalizedStatus() != null && !assignedLeadDTO.getFinalizedStatus().isEmpty()) {
			String[] finalizedStatuses = assignedLeadDTO.getFinalizedStatus().split(",\\s*");
			sqlQuery.append(" AND a.finalized_status IN (:finalizedStatuses)");
		}
		if (assignedLeadDTO.getVisitingDate() != null)
			sqlQuery.append(" AND DATE(a.visiting_date) = :visitingDate");
		if (assignedLeadDTO.getVisitedDate() != null)
			sqlQuery.append(" AND DATE(a.visited_date) = :visitedDate");
		if (assignedLeadDTO.getFinalizedCounsellor() != null)
			sqlQuery.append(" AND a.finalized_counsellor = :finalizedCounsellor");
		if (assignedLeadDTO.getFinalizedProperty() != null)
			sqlQuery.append(" AND a.finalized_property = :finalizedProperty");
		if (assignedLeadDTO.getAssignedTo() != null)
			sqlQuery.append(" AND a.assigned_to = :assignedTo");
		if (assignedLeadDTO.getStartDate() != null && assignedLeadDTO.getEndDate() != null) {
			sqlQuery.append(" AND DATE(a.created_date) BETWEEN :startDate AND :endDate");
		} else if (assignedLeadDTO.getStartDate() != null) {
			sqlQuery.append(" AND DATE(a.created_date) = :startDate");
		}
//		if (assignedLeadDTO.getBranch() != null)
//			sqlQuery.append(" AND u.branch = :branch");
		if (assignedLeadDTO.getLeadSource() != null)
			sqlQuery.append(" AND u.lead_source = :leadSource");
		if (assignedLeadDTO.getLeadFlag() != null)
			sqlQuery.append(" AND u.lead_flag = :leadFlag");

		sqlQuery.append(" GROUP BY a.lead_id ORDER BY a.lead_id ASC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		// Set parameters
		if (assignedLeadDTO.getId() != null)
			query.setParameter("id", assignedLeadDTO.getId());
		if (assignedLeadDTO.getFullName() != null)
			query.setParameter("fullName", assignedLeadDTO.getFullName());
		if (assignedLeadDTO.getPhoneNumber() != null)
			query.setParameter("phoneNumber", assignedLeadDTO.getPhoneNumber());
		if (assignedLeadDTO.getLocation() != null)
			query.setParameter("location", assignedLeadDTO.getLocation());
		if (assignedLeadDTO.getSubLocation() != null)
			query.setParameter("subLocation", assignedLeadDTO.getSubLocation());
		if (assignedLeadDTO.getDataCollector() != null)
			query.setParameter("dataCollector", assignedLeadDTO.getDataCollector());
		if (assignedLeadDTO.getFinalizedProperty() != null && !assignedLeadDTO.getFinalizedProperty().isEmpty()) {
			String[] collegeSecondArray = assignedLeadDTO.getFinalizedProperty().split("\\s*/\\s*");
			for (int i = 0; i < collegeSecondArray.length; i++) {
				query.setParameter("finalizedProperty" + i, "%" + collegeSecondArray[i].trim() + "%");
			}
		}
		if (assignedLeadDTO.getLeadStatus() != null && !assignedLeadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = assignedLeadDTO.getLeadStatus().split(",\\s*");
			query.setParameter("statuses", Arrays.asList(statuses));
		}
		if (assignedLeadDTO.getFinalizedStatus() != null && !assignedLeadDTO.getFinalizedStatus().isEmpty()) {
			String[] finalizedStatuses = assignedLeadDTO.getFinalizedStatus().split(",\\s*");
			query.setParameter("finalizedStatuses", Arrays.asList(finalizedStatuses));
		}
		if (assignedLeadDTO.getVisitingDate() != null)
			query.setParameter("visitingDate", assignedLeadDTO.getVisitingDate());
		if (assignedLeadDTO.getVisitedDate() != null)
			query.setParameter("visitedDate", assignedLeadDTO.getVisitedDate());
		if (assignedLeadDTO.getFinalizedCounsellor() != null)
			query.setParameter("finalizedCounsellor", assignedLeadDTO.getFinalizedCounsellor());
		if (assignedLeadDTO.getFinalizedProperty() != null)
			query.setParameter("finalizedProperty", assignedLeadDTO.getFinalizedProperty());
		if (assignedLeadDTO.getAssignedTo() != null)
			query.setParameter("assignedTo", assignedLeadDTO.getAssignedTo());
		if (assignedLeadDTO.getStartDate() != null && assignedLeadDTO.getEndDate() != null) {
			query.setParameter("startDate", assignedLeadDTO.getStartDate());
			query.setParameter("endDate", assignedLeadDTO.getEndDate());
		} else if (assignedLeadDTO.getStartDate() != null) {
			query.setParameter("startDate", assignedLeadDTO.getStartDate());
		}
//		if (assignedLeadDTO.getBranch() != null)
//			query.setParameter("branch", assignedLeadDTO.getBranch());
		if (assignedLeadDTO.getLeadSource() != null)
			query.setParameter("leadSource", assignedLeadDTO.getLeadSource());
		if (assignedLeadDTO.getLeadFlag() != null)
			query.setParameter("leadFlag", assignedLeadDTO.getLeadFlag());

		query.setFirstResult(assignedLeadDTO.getOffset());
		query.setMaxResults(assignedLeadDTO.getLimit());

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> leadMap = new LinkedHashMap<>();
			leadMap.put("id", result[1]);
			leadMap.put("fullName", result[2]);
			leadMap.put("phoneNumber", result[3]);
			leadMap.put("leadStatus", result[4]);

			returnList.add(leadMap);
		}

		return returnList;
	}

}
