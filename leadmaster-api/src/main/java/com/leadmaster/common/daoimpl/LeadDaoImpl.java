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

import com.leadmaster.common.converter.LeadConverter;
import com.leadmaster.common.dao.LeadDao;
import com.leadmaster.common.domain.Lead;
import com.leadmaster.common.dto.LeadDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.LeadRepository;

@Transactional
@Service("LeadDaoImpl")
public class LeadDaoImpl implements LeadDao {

	private Logger LOGGER = LoggerFactory.getLogger(LeadDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private LeadRepository leadRepository;

	@Override
	public Lead saveLead(LeadDTO leadDTO) {
		Lead lead = LeadConverter.getLeadByLeadDTO(leadDTO);
		return leadRepository.save(lead);

	}

	@Override
	public List<Map<String, Object>> getAllLeads(LeadDTO leadDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT a.id, a.full_name, a.created_date, a.phone_number, a.alternative_number, "
						+ "a.location, a.looking_for, a.tenant_type, a.apartment_type, a.amenities, "
						+ "a.property_approval, a.plot_size, a.assigned_to, a.assigned_by, a.updated_date, "
						+ "a.visiting_date, a.visited_date, a.conversion_date, a.finalized_counsellor, "
						+ "a.finalized_status, a.suggested_plot, a.finalized_property, a.finalized_proof, "
						+ "a.remarks, a.budget_range, a.customer_occupation, a.possession, a.sub_location "
						+ "FROM leads a " + "INNER JOIN users u ON a.assigned_to = u.id " + "WHERE 1=1");

		// Append filters based on leadDTO
		if (leadDTO.getId() != null)
			sqlQuery.append(" AND a.id = :id");
		if (leadDTO.getFullName() != null)
			sqlQuery.append(" AND a.full_name = :fullName");
		if (leadDTO.getPhoneNumber() != null)
			sqlQuery.append(" AND a.phone_number = :phoneNumber");
		if (leadDTO.getDataCollector() != null)
			sqlQuery.append(" AND a.data_collector = :dataCollector");
		if (leadDTO.getLocation() != null)
			sqlQuery.append(" AND a.location = :location");
		if (leadDTO.getSubLocation() != null)
			sqlQuery.append(" AND a.sub_location = :subLocation");
		if (leadDTO.getFinalizedProperty() != null && !leadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedProperties = leadDTO.getFinalizedProperty().split("\\s*/\\s*");
			sqlQuery.append(" AND (");
			for (int i = 0; i < finalizedProperties.length; i++) {
				if (i > 0)
					sqlQuery.append(" OR ");
				sqlQuery.append("a.finalized_property LIKE :finalizedProperty").append(i);
			}
			sqlQuery.append(")");
		}
		if (leadDTO.getRemarks() != null)
			sqlQuery.append(" AND a.remarks = :remarks");
		if (leadDTO.getLeadStatus() != null && !leadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = leadDTO.getLeadStatus().split(",\\s*");
			sqlQuery.append(" AND a.lead_status IN (:statuses)");
		}
		if (leadDTO.getFinalizedStatus() != null && !leadDTO.getFinalizedStatus().isEmpty()) {
			String[] admStatuses = leadDTO.getFinalizedStatus().split(",\\s*");
			sqlQuery.append(" AND a.finalized_status IN (:finalizedStatuses)");
		}
		if (leadDTO.getVisitingDate() != null)
			sqlQuery.append(" AND DATE(a.visiting_date) = :visitingDate");
		if (leadDTO.getVisitedDate() != null)
			sqlQuery.append(" AND DATE(a.visited_date) = :visitedDate");
		if (leadDTO.getConversionDate() != null)
			sqlQuery.append(" AND DATE(a.conversion_date) = :conversionDate");
		if (leadDTO.getFinalizedCounsellor() != null)
			sqlQuery.append(" AND a.finalized_counsellor = :finalizedCounsellor");
		if (leadDTO.getAssignedTo() != null) {
			String[] assignedToValues = leadDTO.getAssignedTo().split(",\\s*");
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
		if (leadDTO.getStartDate() != null && leadDTO.getEndDate() != null) {
			sqlQuery.append(" AND DATE(a.updated_date) BETWEEN :startDate AND :endDate");
		} else if (leadDTO.getStartDate() != null) {
			sqlQuery.append(" AND DATE(a.updated_date) = :startDate");
		}
		if (leadDTO.getBranch() != null)
			sqlQuery.append(" AND u.branch = :branch");

		sqlQuery.append(" ORDER BY a.id ASC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		// Set parameters
		if (leadDTO.getId() != null)
			query.setParameter("id", leadDTO.getId());
		if (leadDTO.getFullName() != null)
			query.setParameter("fullName", leadDTO.getFullName());
		if (leadDTO.getPhoneNumber() != null)
			query.setParameter("phoneNumber", leadDTO.getPhoneNumber());
		if (leadDTO.getDataCollector() != null)
			query.setParameter("dataCollector", leadDTO.getDataCollector());
		if (leadDTO.getLocation() != null)
			query.setParameter("location", leadDTO.getLocation());
		if (leadDTO.getSubLocation() != null)
			query.setParameter("subLocation", leadDTO.getSubLocation());
		if (leadDTO.getFinalizedProperty() != null && !leadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedProperties = leadDTO.getFinalizedProperty().split("\\s*/\\s*");
			for (int i = 0; i < finalizedProperties.length; i++) {
				query.setParameter("finalizedProperty" + i, "%" + finalizedProperties[i].trim() + "%");
			}
		}
		if (leadDTO.getRemarks() != null)
			query.setParameter("remarks", leadDTO.getRemarks());
		if (leadDTO.getLeadStatus() != null && !leadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = leadDTO.getLeadStatus().split(",\\s*");
			query.setParameter("statuses", Arrays.asList(statuses));
		}
		if (leadDTO.getFinalizedStatus() != null && !leadDTO.getFinalizedStatus().isEmpty()) {
			String[] finalizedStatuses = leadDTO.getFinalizedStatus().split(",\\s*");
			query.setParameter("finalizedStatuses", Arrays.asList(finalizedStatuses));
		}
		if (leadDTO.getVisitingDate() != null)
			query.setParameter("visitingDate", leadDTO.getVisitingDate());
		if (leadDTO.getVisitedDate() != null)
			query.setParameter("visitedDate", leadDTO.getVisitedDate());
		if (leadDTO.getConversionDate() != null)
			query.setParameter("conversionDate", leadDTO.getConversionDate());
		if (leadDTO.getFinalizedCounsellor() != null)
			query.setParameter("finalizedCounsellor", leadDTO.getFinalizedCounsellor());
		if (leadDTO.getAssignedTo() != null) {
			String[] assignedToValues = leadDTO.getAssignedTo().split(",\\s*");
			for (int i = 0; i < assignedToValues.length; i++) {
				query.setParameter("assignedTo" + i, assignedToValues[i]);
			}
		}
		if (leadDTO.getStartDate() != null && leadDTO.getEndDate() != null) {
			query.setParameter("startDate", leadDTO.getStartDate());
			query.setParameter("endDate", leadDTO.getEndDate());
		} else if (leadDTO.getStartDate() != null) {
			query.setParameter("startDate", leadDTO.getStartDate());
		}
		if (leadDTO.getBranch() != null)
			query.setParameter("branch", leadDTO.getBranch());

		// Apply pagination
		query.setFirstResult(leadDTO.getOffset());
		query.setMaxResults(leadDTO.getLimit());

		List<Object[]> resultList = query.getResultList();

		// Map results to List of Maps
		for (Object[] result : resultList) {
			Map<String, Object> leadMap = new LinkedHashMap<>();
			leadMap.put("id", result[0]);
			leadMap.put("fullName", result[1]);
			leadMap.put("phoneNumber", result[3]);
			leadMap.put("createdDate", result[2]);
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
			leadMap.put("budgetRange", result[24]);
			leadMap.put("customerOccupation", result[25]);
			leadMap.put("posession", result[26]);
			leadMap.put("subLocation", result[27]);

			returnList.add(leadMap);
		}
		return returnList;
	}

	@Override
	public List<Map<String, Object>> getAllLeadsCount(LeadDTO leadDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder("SELECT COUNT(*) AS total_count FROM leads a "
				+ "INNER JOIN users u ON a.assigned_to = u.id " + "WHERE 1=1");

		if (leadDTO.getId() != null)
			sqlQuery.append(" AND a.id = :id");
		if (leadDTO.getFullName() != null)
			sqlQuery.append(" AND a.full_name = :fullName");
		if (leadDTO.getPhoneNumber() != null)
			sqlQuery.append(" AND a.phone_number = :phoneNumber");
		if (leadDTO.getDataCollector() != null)
			sqlQuery.append(" AND a.data_collector = :dataCollector");
		if (leadDTO.getLocation() != null) {
			String[] locationValues = leadDTO.getLocation().split(",\\s*");
			sqlQuery.append(" AND (");
			for (int i = 0; i < locationValues.length; i++) {
				if (i > 0)
					sqlQuery.append(" OR ");
				sqlQuery.append("FIND_IN_SET(:location").append(i).append(", a.location) > 0");
			}
			sqlQuery.append(")");
		}
		if (leadDTO.getFinalizedProperty() != null && !leadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedProperties = leadDTO.getFinalizedProperty().split("\\s*/\\s*");
			sqlQuery.append(" AND (");
			for (int i = 0; i < finalizedProperties.length; i++) {
				if (i > 0)
					sqlQuery.append(" OR ");
				sqlQuery.append("a.finalized_property LIKE :finalizedProperty").append(i);
			}
			sqlQuery.append(")");
		}
		if (leadDTO.getRemarks() != null)
			sqlQuery.append(" AND a.remarks = :remarks");
		if (leadDTO.getLeadStatus() != null && !leadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = leadDTO.getLeadStatus().split(",\\s*");
			sqlQuery.append(" AND a.lead_status IN (:statuses)");
		}
		if (leadDTO.getFinalizedStatus() != null && !leadDTO.getFinalizedStatus().isEmpty()) {
			String[] admStatuses = leadDTO.getFinalizedStatus().split(",\\s*");
			sqlQuery.append(" AND a.finalized_status IN (:finalizedStatuses)");
		}
		if (leadDTO.getVisitingDate() != null)
			sqlQuery.append(" AND DATE(a.visiting_date) = :visitingDate");
		if (leadDTO.getVisitedDate() != null)
			sqlQuery.append(" AND DATE(a.visited_date) = :visitedDate");
		if (leadDTO.getConversionDate() != null)
			sqlQuery.append(" AND DATE(a.conversion_date) = :conversionDate");
		if (leadDTO.getFinalizedCounsellor() != null)
			sqlQuery.append(" AND a.finalized_counsellor = :finalizedCounsellor");
		if (leadDTO.getAssignedTo() != null) {
			String[] assignedToValues = leadDTO.getAssignedTo().split(",\\s*");
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
		if (leadDTO.getStartDate() != null && leadDTO.getEndDate() != null) {
			sqlQuery.append(" AND DATE(a.updated_date) BETWEEN :startDate AND :endDate");
		} else if (leadDTO.getStartDate() != null) {
			sqlQuery.append(" AND DATE(a.updated_date) = :startDate");
		}
		if (leadDTO.getBranch() != null)
			sqlQuery.append(" AND u.branch = :branch");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (leadDTO.getId() != null)
			query.setParameter("id", leadDTO.getId());
		if (leadDTO.getFullName() != null)
			query.setParameter("fullName", leadDTO.getFullName());
		if (leadDTO.getPhoneNumber() != null)
			query.setParameter("phoneNumber", leadDTO.getPhoneNumber());
		if (leadDTO.getDataCollector() != null)
			query.setParameter("dataCollector", leadDTO.getDataCollector());
		if (leadDTO.getLocation() != null) {
			String[] locationValues = leadDTO.getLocation().split(",\\s*");
			for (int i = 0; i < locationValues.length; i++) {
				query.setParameter("location" + i, locationValues[i]);
			}
		}
		if (leadDTO.getFinalizedProperty() != null && !leadDTO.getFinalizedProperty().isEmpty()) {
			String[] finalizedProperties = leadDTO.getFinalizedProperty().split("\\s*/\\s*");
			for (int i = 0; i < finalizedProperties.length; i++) {
				query.setParameter("finalizedProperty" + i, "%" + finalizedProperties[i].trim() + "%");
			}
		}
		if (leadDTO.getRemarks() != null)
			query.setParameter("remarks", leadDTO.getRemarks());
		if (leadDTO.getLeadStatus() != null && !leadDTO.getLeadStatus().isEmpty()) {
			String[] statuses = leadDTO.getLeadStatus().split(",\\s*");
			query.setParameter("statuses", Arrays.asList(statuses));
		}
		if (leadDTO.getFinalizedStatus() != null && !leadDTO.getFinalizedStatus().isEmpty()) {
			String[] finalizedStatuses = leadDTO.getFinalizedStatus().split(",\\s*");
			query.setParameter("finalizedStatuses", Arrays.asList(finalizedStatuses));
		}
		if (leadDTO.getVisitingDate() != null)
			query.setParameter("visitingDate", leadDTO.getVisitingDate());
		if (leadDTO.getVisitedDate() != null)
			query.setParameter("visitedDate", leadDTO.getVisitedDate());
		if (leadDTO.getConversionDate() != null)
			query.setParameter("conversionDate", leadDTO.getConversionDate());
		if (leadDTO.getFinalizedCounsellor() != null)
			query.setParameter("finalizedCounsellor", leadDTO.getFinalizedCounsellor());
		if (leadDTO.getAssignedTo() != null) {
			String[] assignedToValues = leadDTO.getAssignedTo().split(",\\s*");
			for (int i = 0; i < assignedToValues.length; i++) {
				query.setParameter("assignedTo" + i, assignedToValues[i]);
			}
		}
		if (leadDTO.getStartDate() != null && leadDTO.getEndDate() != null) {
			query.setParameter("startDate", leadDTO.getStartDate());
			query.setParameter("endDate", leadDTO.getEndDate());
		} else if (leadDTO.getStartDate() != null) {
			query.setParameter("startDate", leadDTO.getStartDate());
		}
		if (leadDTO.getBranch() != null)
			query.setParameter("branch", leadDTO.getBranch());

		Object result = query.getSingleResult();

		Map<String, Object> leadCountMap = new LinkedHashMap<>();
		leadCountMap.put("totalCount", result);
		returnList.add(leadCountMap);

		return returnList;
	}

	@Override
	public List<Lead> getAllLead(LeadDTO leadDTO) {
		List<Lead> returnLeadList = null;
		StringBuffer sqlQuery = new StringBuffer("from Lead a where 1=1");

		if (null != leadDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != leadDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != leadDTO.getAssignedBy())
			sqlQuery.append(" AND a.assignedBy = :assignedBy");

		sqlQuery.append(" order by a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != leadDTO.getId())
			query.setParameter("id", leadDTO.getId());
		if (null != leadDTO.getStatus())
			query.setParameter("status", leadDTO.getStatus());
		if (null != leadDTO.getAssignedBy())
			query.setParameter("assignedBy", leadDTO.getAssignedBy());

		query.setFirstResult(leadDTO.getOffset());
		query.setMaxResults(leadDTO.getLimit());

		returnLeadList = query.getResultList();

		return returnLeadList;
	}

	@Override
	public Lead getLeadById(Long id) {
		Optional<Lead> lead = leadRepository.findById(id);
		if (!lead.isPresent())
			throw new ResourceNotFoundException("The Lead is not found in the system. id:" + id);
		return lead.get();
	}

	@Override
	public List<Lead> getLeadByPhone(String phoneNumber) {
		return leadRepository.getLeadByPhone(phoneNumber);
	}

}