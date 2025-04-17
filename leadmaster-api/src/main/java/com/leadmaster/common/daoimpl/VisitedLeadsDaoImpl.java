package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.VisitedLeadsConverter;
import com.leadmaster.common.dao.VisitedLeadsDao;
import com.leadmaster.common.domain.VisitedLeads;
import com.leadmaster.common.dto.VisitedLeadsDTO;
import com.leadmaster.common.repository.VisitedLeadsRepository;

@Transactional
@Service("VisitedLeadsDaoImpl")
public class VisitedLeadsDaoImpl implements VisitedLeadsDao {

	private Logger LOGGER = LoggerFactory.getLogger(LeadDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VisitedLeadsRepository visitedLeadsRepository;

	@Override
	public VisitedLeads saveVisitedLead(VisitedLeadsDTO visitedLeadsDTO) {
		VisitedLeads visitedLeads = VisitedLeadsConverter.getVisitedLeadsByVisitedLeadsDTO(visitedLeadsDTO);
		return visitedLeadsRepository.save(visitedLeads);

	}

	@Override
	public List<Map<String, Object>> getVisitedLeads(VisitedLeadsDTO visitedLeadsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT b.id, a.lead_id, b.full_name, b.phone_number, b.location, a.property, "
						+ "a.visited_date, a.remarks, b.lead_status, a.created_by, b.sub_location "
						+ "FROM visited_leads a " + "INNER JOIN assigned_leads b ON a.lead_id = b.lead_id "
						+ "INNER JOIN users u ON b.assigned_to = u.id " + "WHERE 1=1");

		if (visitedLeadsDTO.getId() != null)
			sqlQuery.append(" AND a.id = :id");
		if (visitedLeadsDTO.getFullName() != null)
			sqlQuery.append(" AND b.full_name = :fullName");
		if (visitedLeadsDTO.getPhoneNumber() != null)
			sqlQuery.append(" AND b.phone_number = :phoneNumber");
		if (visitedLeadsDTO.getLocation() != null)
			sqlQuery.append(" AND b.location = :location");
		if (visitedLeadsDTO.getSubLocation() != null)
			sqlQuery.append(" AND b.sub_location = :subLocation");
		if (visitedLeadsDTO.getProperty() != null)
			sqlQuery.append(" AND a.property = :property");
		if (visitedLeadsDTO.getVisitStartDate() != null && visitedLeadsDTO.getVisitEndDate() != null)
			sqlQuery.append(" AND a.visited_date BETWEEN :visitStartDate AND :visitEndDate");
		else if (visitedLeadsDTO.getVisitStartDate() != null)
			sqlQuery.append(" AND a.visited_date = :visitedDate");
		if (visitedLeadsDTO.getCreatedBy() != null)
			sqlQuery.append(" AND a.created_by = :createdBy");
//		if (visitedLeadsDTO.getBranch() != null)
//			sqlQuery.append(" AND u.branch = :branch");
		if (visitedLeadsDTO.getBranch() != null)
			sqlQuery.append(" AND b.assigned_to = :assignedTo");
		if (visitedLeadsDTO.getTeamLead() != null)
			sqlQuery.append(" AND u.team_lead = :teamLead");
		if (visitedLeadsDTO.getMarketingExecutive() != null)
			sqlQuery.append(" AND u.marketing_executive = :marketingExecutive");

		sqlQuery.append(" ORDER BY a.id DESC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (visitedLeadsDTO.getId() != null)
			query.setParameter("id", visitedLeadsDTO.getId());
		if (visitedLeadsDTO.getFullName() != null)
			query.setParameter("fullName", visitedLeadsDTO.getFullName());
		if (visitedLeadsDTO.getPhoneNumber() != null)
			query.setParameter("phoneNumber", visitedLeadsDTO.getPhoneNumber());
		if (visitedLeadsDTO.getLocation() != null)
			query.setParameter("location", visitedLeadsDTO.getLocation());
		if (visitedLeadsDTO.getSubLocation() != null)
			query.setParameter("subLocation", visitedLeadsDTO.getSubLocation());
		if (visitedLeadsDTO.getProperty() != null)
			query.setParameter("property", visitedLeadsDTO.getProperty());
		if (visitedLeadsDTO.getVisitStartDate() != null && visitedLeadsDTO.getVisitEndDate() != null) {
			query.setParameter("visitStartDate", visitedLeadsDTO.getVisitStartDate());
			query.setParameter("visitEndDate", visitedLeadsDTO.getVisitEndDate());
		} else if (visitedLeadsDTO.getVisitStartDate() != null) {
			query.setParameter("visitedDate", visitedLeadsDTO.getVisitStartDate());
		}
		if (visitedLeadsDTO.getCreatedBy() != null)
			query.setParameter("createdBy", visitedLeadsDTO.getCreatedBy());
//		if (visitedLeadsDTO.getBranch() != null)
//			query.setParameter("branch", visitedLeadsDTO.getBranch());
		if (visitedLeadsDTO.getAssignedTo() != null)
			query.setParameter("assignedTo", visitedLeadsDTO.getAssignedTo());
		if (visitedLeadsDTO.getTeamLead() != null)
			query.setParameter("teamLead", visitedLeadsDTO.getTeamLead());
		if (visitedLeadsDTO.getMarketingExecutive() != null)
			query.setParameter("marketingExecutive", visitedLeadsDTO.getMarketingExecutive());

		query.setFirstResult(visitedLeadsDTO.getOffset());
		query.setMaxResults(visitedLeadsDTO.getLimit());

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> leadMap = new LinkedHashMap<>();
			leadMap.put("id", result[0]);
			leadMap.put("leadId", result[1]);
			leadMap.put("fullName", result[2]);
			leadMap.put("phoneNumber", result[3]);
			leadMap.put("location", result[4]);
			leadMap.put("property", result[5]);
			leadMap.put("visitedDate", result[6]);
			leadMap.put("remarks", result[7]);
			leadMap.put("leadStatus", result[8]);
			leadMap.put("createdBy", result[9]);
			leadMap.put("subLocation", result[10]);

			returnList.add(leadMap);
		}

		return returnList;
	}

}
