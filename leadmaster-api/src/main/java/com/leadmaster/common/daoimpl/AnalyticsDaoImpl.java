package com.leadmaster.common.daoimpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leadmaster.common.dao.AnalyticsDao;
import com.leadmaster.common.dto.AnalyticsDTO;

@Transactional
@Service("AnalyticsDaoImpl")
public class AnalyticsDaoImpl implements AnalyticsDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Map<String, Object>> getConversionAnalytics(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Query to get counts for 'Admitted', 'Visiting', etc. from leads table
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT l.lead_status, COUNT(l.id) AS count ").append("FROM assigned_leads l ")
				.append("JOIN users u ON l.assigned_to = u.id ")
				.append("WHERE l.lead_status IN ('Purchased', 'Finalized', 'Visiting', 'Hot', 'Warm', 'Cold')");

//		if (analyticsDTO.getBranch() != null) {
//			sqlQuery.append(" AND u.branch = :branch");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			sqlQuery.append(" AND l.assigned_to = :assignedTo");
		}

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				sqlQuery.append(
						" AND ((l.lead_status = 'Finalized' AND l.conversion_date BETWEEN :startDate AND :endDate) ")
						.append("OR (l.lead_status = 'Visiting' AND l.visiting_date BETWEEN :startDate AND :endDate) ")
						.append("OR ((l.lead_status NOT IN ('Finalized', 'Visiting')) AND l.updated_date BETWEEN :startDate AND :endDate))");
			} else {
				sqlQuery.append(" AND ((l.lead_status = 'Finalized' AND DATE(l.conversion_date) = :startDate) ")
						.append("OR (l.lead_status = 'Visiting' AND DATE(l.visiting_date) = :startDate) ")
						.append("OR ((l.lead_status NOT IN ('Finalized', 'Visiting')) AND DATE(l.updated_date) = :startDate))");
			}
		}

		sqlQuery.append(" GROUP BY l.lead_status");

		// Query to get count for 'Visited' from visited_leads table
		StringBuilder visitedLeadsQuery = new StringBuilder();
		visitedLeadsQuery.append("SELECT 'Visited' AS lead_status, COUNT(v.id) AS count ")
				.append("FROM visited_leads v ").append("JOIN users u ON v.created_by = u.id ").append("WHERE 1=1");

//		if (analyticsDTO.getBranch() != null) {
//			visitedLeadsQuery.append(" AND u.branch = :branch");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			visitedLeadsQuery.append(" AND v.created_by = :assignedTo");
		}

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				visitedLeadsQuery.append(" AND v.updated_date BETWEEN :startDate AND :endDate");
			} else {
				visitedLeadsQuery.append(" AND DATE(v.updated_date) = :startDate");
			}
		}

		visitedLeadsQuery.append(" GROUP BY lead_status");

		// Combine both queries using UNION
		String finalQuery = sqlQuery.toString() + " UNION " + visitedLeadsQuery.toString()
				+ " ORDER BY lead_status ASC";

		Query query = entityManager.createNativeQuery(finalQuery);

//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}
		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
		}
		if (analyticsDTO.getEndDate() != null) {
			query.setParameter("endDate", analyticsDTO.getEndDate());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> statusMap = new LinkedHashMap<>();
			statusMap.put("leadStatus", result[0]);
			statusMap.put("count", result[1]);

			returnList.add(statusMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getFailureAnalytics(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT l.lead_status, COUNT(l.id) AS count ").append("FROM leads l ")
				.append("JOIN users u ON l.assigned_to = u.id ")
				.append("WHERE l.lead_status NOT IN ('Purchased', 'Finalized', 'Visiting', 'Visited', 'Assigned', 'Hot', 'Warm', 'Cold', 'Fresh')");

//		if (analyticsDTO.getBranch() != null) {
//			sqlQuery.append(" AND u.branch = :branch");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			sqlQuery.append(" AND l.data_collector = :assignedTo");
		}

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				sqlQuery.append(" AND l.updated_date BETWEEN :startDate AND :endDate");
			} else {
				sqlQuery.append(" AND DATE(l.updated_date) = :startDate");
			}
		}

		sqlQuery.append(" GROUP BY l.lead_status");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}
		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
		}
		if (analyticsDTO.getEndDate() != null) {
			query.setParameter("endDate", analyticsDTO.getEndDate());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> statusMap = new LinkedHashMap<>();
			statusMap.put("leadStatus", result[0]);
			statusMap.put("count", result[1]);

			returnList.add(statusMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getDataCoverage(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Query to get overall count of leads
		StringBuilder overallQuery = new StringBuilder();
		overallQuery.append("SELECT COUNT(l.id) AS overallCount FROM leads l ")
				.append("JOIN users u ON l.created_by = u.id WHERE 1=1");

//		if (analyticsDTO.getBranch() != null) {
//			overallQuery.append(" AND u.branch = :branch");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			overallQuery.append(" AND l.assigned_to = :assignedTo");
		}

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				overallQuery.append(" AND l.updated_date BETWEEN :startDate AND :endDate");
			} else {
				overallQuery.append(" AND DATE(l.updated_date) = :startDate");
			}
		}

		Query overallCountQuery = entityManager.createNativeQuery(overallQuery.toString());

//		if (analyticsDTO.getBranch() != null) {
//			overallCountQuery.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			overallCountQuery.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}
		if (analyticsDTO.getStartDate() != null) {
			overallCountQuery.setParameter("startDate", analyticsDTO.getStartDate());
		}
		if (analyticsDTO.getEndDate() != null) {
			overallCountQuery.setParameter("endDate", analyticsDTO.getEndDate());
		}

		Long overallCount = ((BigInteger) overallCountQuery.getSingleResult()).longValue();

		// Query to get count of fresh leads
		StringBuilder freshLeadsQuery = new StringBuilder();
		freshLeadsQuery.append("SELECT COUNT(l.id) AS freshCount FROM leads l ")
				.append("JOIN users u ON l.created_by = u.id WHERE l.lead_status = 'Fresh'");

//		if (analyticsDTO.getBranch() != null) {
//			freshLeadsQuery.append(" AND u.branch = :branch");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			freshLeadsQuery.append(" AND l.assigned_to = :assignedTo");
		}

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				freshLeadsQuery.append(" AND l.updated_date BETWEEN :startDate AND :endDate");
			} else {
				freshLeadsQuery.append(" AND DATE(l.updated_date) = :startDate");
			}
		}

		Query freshCountQuery = entityManager.createNativeQuery(freshLeadsQuery.toString());

//		if (analyticsDTO.getBranch() != null) {
//			freshCountQuery.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			freshCountQuery.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}
		if (analyticsDTO.getStartDate() != null) {
			freshCountQuery.setParameter("startDate", analyticsDTO.getStartDate());
		}
		if (analyticsDTO.getEndDate() != null) {
			freshCountQuery.setParameter("endDate", analyticsDTO.getEndDate());
		}

		Long freshCount = ((BigInteger) freshCountQuery.getSingleResult()).longValue();

		// Calculate data coverage and data follow-up counts
		long dataCoveredCount = overallCount - freshCount;
		long dataFollowUpCount = freshCount;

		// Prepare result map for overall data
		Map<String, Object> overallData = new LinkedHashMap<>();
		overallData.put("overallCount", overallCount);
		overallData.put("dataCovered", dataCoveredCount);
		overallData.put("dataFollowUp", dataFollowUpCount);

		// Add overall data to return list
		returnList.add(overallData);

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getTeamCalls(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		StringBuilder callsQuery = new StringBuilder();
		callsQuery.append("SELECT u.name, COUNT(DISTINCT lt.id) AS calls, GROUP_CONCAT(DISTINCT r.role) AS roles, ")
				.append("SUBSTRING_INDEX(u.email, '@', 1) AS username ").append("FROM lead_track lt ")
				.append("JOIN users u ON lt.created_by = u.id ").append("JOIN user_roles r ON u.id = r.user_id ")
				.append("WHERE 1=1 ");

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				callsQuery.append("AND lt.created_date BETWEEN :startDate AND :endDate ");
			} else {
				callsQuery.append("AND DATE(lt.created_date) = :startDate ");
			}
		}

//		if (analyticsDTO.getBranch() != null) {
//			callsQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			callsQuery.append("AND lt.created_by = :assignedTo ");
		}

		callsQuery.append("GROUP BY u.id, u.name, u.email ").append("ORDER BY calls ASC");

		Query query = entityManager.createNativeQuery(callsQuery.toString());

		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
			if (analyticsDTO.getEndDate() != null) {
				query.setParameter("endDate", analyticsDTO.getEndDate());
			}
		}
//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> userCallMap = new LinkedHashMap<>();
			userCallMap.put("name", result[0]);
			userCallMap.put("email", result[3]);
			userCallMap.put("role", result[2]);
			userCallMap.put("calls", result[1]);

			returnList.add(userCallMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getCollegeAdmissions(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Query to get count of admissions by college
		StringBuilder admissionsQuery = new StringBuilder();
		admissionsQuery.append("SELECT l.finalized_property, COUNT(l.id) AS admissions ")
				.append("FROM assigned_leads l ").append("JOIN users u ON l.assigned_to = u.id ")
				.append("WHERE l.lead_status = 'Purchased' AND l.finalized_status = 'Approved' ");

//		if (analyticsDTO.getBranch() != null) {
//			admissionsQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			admissionsQuery.append("AND l.assigned_to = :assignedTo ");
		}

		admissionsQuery.append("GROUP BY l.finalized_property ").append("ORDER BY admissions DESC");

		Query query = entityManager.createNativeQuery(admissionsQuery.toString());

//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> collegeAdmissionMap = new LinkedHashMap<>();
			collegeAdmissionMap.put("college", result[0]);
			collegeAdmissionMap.put("admissions", result[1]);

			returnList.add(collegeAdmissionMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getGraphData(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Query to get count of leads tracked by date
		StringBuilder graphDataQuery = new StringBuilder();
		graphDataQuery.append("SELECT DATE(lt.created_date) AS date, COUNT(lt.id) AS count ")
				.append("FROM lead_track lt ").append("JOIN leads l ON lt.lead_id = l.id ")
				.append("JOIN users u ON lt.created_by = u.id ").append("WHERE 1=1 ");

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				graphDataQuery.append("AND lt.created_date BETWEEN :startDate AND :endDate ");
			} else {
				graphDataQuery.append("AND DATE(lt.created_date) = :startDate ");
			}
		} else {
			// Default to last 10 days
			graphDataQuery.append("AND lt.created_date >= CURDATE() - INTERVAL 10 DAY ");
		}

//		if (analyticsDTO.getBranch() != null) {
//			graphDataQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			graphDataQuery.append("AND l.assigned_to = :assignedTo ");
		}

		graphDataQuery.append("GROUP BY DATE(lt.created_date) ").append("ORDER BY DATE(lt.created_date) DESC ");

		Query query = entityManager.createNativeQuery(graphDataQuery.toString());

		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
			if (analyticsDTO.getEndDate() != null) {
				query.setParameter("endDate", analyticsDTO.getEndDate());
			}
		}
//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> dateCountMap = new LinkedHashMap<>();
			dateCountMap.put("date", result[0]);
			dateCountMap.put("count", result[1]);

			returnList.add(dateCountMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getCurrentLeadTracking(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Setting the time zone to Indian Standard Time (IST)
		String timeZoneQuery = "SET time_zone = '+05:30'";

		// Query to get user activity, time difference from current time, role, and
		// phone number
		StringBuilder graphDataQuery = new StringBuilder();
		graphDataQuery.append("SELECT u.name, TIMESTAMPDIFF(MINUTE, MAX(lt.created_date), NOW()) AS minutesAgo, ")
				.append("ur.role, u.phone_number ").append("FROM lead_track lt ")
				.append("JOIN leads l ON lt.lead_id = l.id ").append("JOIN users u ON lt.created_by = u.id ")
				.append("JOIN user_roles ur ON ur.user_id = u.id ").append("WHERE 1=1 ");

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				graphDataQuery.append("AND lt.created_date BETWEEN :startDate AND :endDate ");
			} else {
				graphDataQuery.append("AND DATE(lt.created_date) = :startDate ");
			}
		} else {
			// Default to last 10 days
			graphDataQuery.append("AND lt.created_date >= CURDATE() - INTERVAL 10 DAY ");
		}

//		if (analyticsDTO.getBranch() != null) {
//			graphDataQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			graphDataQuery.append("AND l.assigned_to = :assignedTo ");
		}

		graphDataQuery.append("AND ur.role NOT IN ('Super Admin', 'Admin') ")
				.append("AND TIME(lt.created_date) >= '09:30:00' AND TIME(lt.created_date) <= '18:30:00' ")
				.append("AND NOT ((TIME(lt.created_date) >= '13:30:00' AND TIME(lt.created_date) <= '14:00:00') OR ")
				.append("(TIME(lt.created_date) >= '16:00:00' AND TIME(lt.created_date) <= '16:15:00')) ")
				.append("GROUP BY u.name, ur.role, u.phone_number ").append("ORDER BY minutesAgo DESC");

		// Execute the time zone setting query
		entityManager.createNativeQuery(timeZoneQuery).executeUpdate();

		Query query = entityManager.createNativeQuery(graphDataQuery.toString());

		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
			if (analyticsDTO.getEndDate() != null) {
				query.setParameter("endDate", analyticsDTO.getEndDate());
			}
		}
//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> userActivityMap = new LinkedHashMap<>();
			userActivityMap.put("name", result[0]);
			userActivityMap.put("minutesAgo", result[1]);
			userActivityMap.put("role", result[2]);
			userActivityMap.put("phoneNumber", result[3]);

			returnList.add(userActivityMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getCallAverageDifference(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Setting the time zone to Indian Standard Time (IST)
		String timeZoneQuery = "SET time_zone = '+05:30'";
		entityManager.createNativeQuery(timeZoneQuery).executeUpdate();

		// Query to get user activity and average time difference between consecutive
		// created_date entries
		StringBuilder graphDataQuery = new StringBuilder();
		graphDataQuery.append("WITH LeadTimeDiffs AS (").append("    SELECT ").append("        u.name, ")
				.append("        TIMESTAMPDIFF(MINUTE, ")
				.append("            LAG(lt.created_date) OVER (PARTITION BY u.id ORDER BY lt.created_date), ")
				.append("            lt.created_date) AS timeDiff ").append("    FROM lead_track lt ")
				.append("    JOIN leads l ON lt.lead_id = l.id ").append("    JOIN users u ON lt.created_by = u.id ")
				.append("    JOIN user_roles ur ON ur.user_id = u.id ").append("    WHERE 1=1 ");

		// Adding filters based on AnalyticsDTO parameters
		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				graphDataQuery.append("AND lt.created_date BETWEEN :startDate AND :endDate ");
			} else {
				graphDataQuery.append("AND DATE(lt.created_date) = :startDate ");
			}
		} else {
			graphDataQuery.append("AND lt.created_date >= CURDATE() - INTERVAL 10 DAY ");
		}

//		if (analyticsDTO.getBranch() != null) {
//			graphDataQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			graphDataQuery.append("AND l.assigned_to = :assignedTo ");
		}

		graphDataQuery.append("AND ur.role NOT IN ('Super Admin', 'Admin') ")
				.append("AND TIME(lt.created_date) BETWEEN '09:30:00' AND '18:30:00' ").append(") ")
				.append("SELECT name, ").append("AVG(timeDiff) AS avgMinutesAgo ").append("FROM LeadTimeDiffs ")
				.append("WHERE timeDiff IS NOT NULL ") // Exclude nulls (first entry for each user)
				.append("GROUP BY name ").append("ORDER BY avgMinutesAgo DESC");

		Query query = entityManager.createNativeQuery(graphDataQuery.toString());

		// Setting parameters based on AnalyticsDTO values
		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
			if (analyticsDTO.getEndDate() != null) {
				query.setParameter("endDate", analyticsDTO.getEndDate());
			}
		}
//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		// Processing query results and formatting average minutes ago
		for (Object[] result : resultList) {
			Map<String, Object> userActivityMap = new LinkedHashMap<>();
			userActivityMap.put("name", result[0]);

			// Convert average minutes to hours and minutes format
			long avgMinutes = ((Number) result[1]).longValue();
			long adjustedAvgMinutes = Math.max(avgMinutes, 0); // Ensure no negative values

			long hours = adjustedAvgMinutes / 60;
			long minutes = adjustedAvgMinutes % 60;

			// Build formatted string "X hours Y minutes"
			String formattedTime = hours + " hours " + minutes + " minutes";

			userActivityMap.put("avgMinutesAgo", formattedTime);
			returnList.add(userActivityMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getGetMyCollegeAnalytics(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		// Query to get counts for 'Admitted', 'Visiting', etc. from leads table
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT l.lead_flag, COUNT(l.id) AS count ").append("FROM assigned_leads l ")
				.append("JOIN users u ON l.assigned_to = u.id ").append("WHERE l.lead_source = 'GetMyProperty'");

//		if (analyticsDTO.getBranch() != null) {
//			sqlQuery.append(" AND u.branch = :branch");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			sqlQuery.append(" AND l.assigned_to = :assignedTo");
		}

		if (analyticsDTO.getStartDate() != null) {
			sqlQuery.append(" AND DATE(l.created_date) >= DATE(:startDate)");
		}

		if (analyticsDTO.getEndDate() != null) {
			sqlQuery.append(" AND DATE(l.created_date) <= DATE(:endDate)");
		}

		sqlQuery.append(" GROUP BY l.lead_flag");

		// Create and configure the query
		Query query = entityManager.createNativeQuery(sqlQuery.toString());

//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}
		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
		}
		if (analyticsDTO.getEndDate() != null) {
			query.setParameter("endDate", analyticsDTO.getEndDate());
		}

		// Execute the query and process the results
		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> statusMap = new LinkedHashMap<>();
			statusMap.put("leadFlags", result[0]);
			statusMap.put("count", result[1]);

			returnList.add(statusMap);
		}

		return returnList;
	}

	// individual followups
	@Override
	public List<Map<String, Object>> getIndividualFollowups(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		StringBuilder callsQuery = new StringBuilder();
		callsQuery.append("SELECT u.name, COUNT(DISTINCT al.id) AS followups, GROUP_CONCAT(DISTINCT r.role) AS roles, ")
				.append("SUBSTRING_INDEX(u.email, '@', 1) AS username ").append("FROM assigned_leads al ")
				.append("JOIN users u ON al.assigned_to = u.id ").append("JOIN user_roles r ON u.id = r.user_id ")
				.append("WHERE al.lead_status IN ('Hot', 'Warm', 'Visiting', 'Visited') ");

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				callsQuery.append("AND al.updated_date BETWEEN :startDate AND :endDate ");
			} else {
				callsQuery.append("AND DATE(al.updated_date) = :startDate ");
			}
		}

//		if (analyticsDTO.getBranch() != null) {
//			callsQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			callsQuery.append("AND al.assigned_to = :assignedTo ");
		}

		callsQuery.append("GROUP BY u.id, u.name, u.email ").append("ORDER BY followups ASC");

		Query query = entityManager.createNativeQuery(callsQuery.toString());

		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
			if (analyticsDTO.getEndDate() != null) {
				query.setParameter("endDate", analyticsDTO.getEndDate());
			}
		}
//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> userCallMap = new LinkedHashMap<>();
			userCallMap.put("name", result[0]);
			userCallMap.put("email", result[3]);
//			userCallMap.put("role", result[2]);
			userCallMap.put("followups", result[1]);

			returnList.add(userCallMap);
		}

		return returnList;
	}

	// level one followups
	@Override
	public List<Map<String, Object>> getLevelOneFollowups(AnalyticsDTO analyticsDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		StringBuilder callsQuery = new StringBuilder();
		callsQuery.append("SELECT u.name, COUNT(DISTINCT al.id) AS followups, GROUP_CONCAT(DISTINCT r.role) AS roles, ")
				.append("SUBSTRING_INDEX(u.email, '@', 1) AS username ").append("FROM leads al ")
				.append("JOIN users u ON al.data_collector = u.id ").append("JOIN user_roles r ON u.id = r.user_id ")
				.append("WHERE al.lead_status IN ('FollowUp', 'Call-Immediately') ");

		if (analyticsDTO.getStartDate() != null) {
			if (analyticsDTO.getEndDate() != null) {
				callsQuery.append("AND lt.updated_date BETWEEN :startDate AND :endDate ");
			} else {
				callsQuery.append("AND DATE(lt.updated_date) = :startDate ");
			}
		}

//		if (analyticsDTO.getBranch() != null) {
//			callsQuery.append("AND u.branch = :branch ");
//		}

		if (analyticsDTO.getAssignedTo() != null) {
			callsQuery.append("AND al.data_collector = :assignedTo ");
		}

		callsQuery.append("GROUP BY u.id, u.name, u.email ").append("ORDER BY followups ASC");

		Query query = entityManager.createNativeQuery(callsQuery.toString());

		if (analyticsDTO.getStartDate() != null) {
			query.setParameter("startDate", analyticsDTO.getStartDate());
			if (analyticsDTO.getEndDate() != null) {
				query.setParameter("endDate", analyticsDTO.getEndDate());
			}
		}
//		if (analyticsDTO.getBranch() != null) {
//			query.setParameter("branch", analyticsDTO.getBranch());
//		}
		if (analyticsDTO.getAssignedTo() != null) {
			query.setParameter("assignedTo", analyticsDTO.getAssignedTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> userCallMap = new LinkedHashMap<>();
			userCallMap.put("name", result[0]);
			userCallMap.put("email", result[3]);
//			userCallMap.put("role", result[2]);
			userCallMap.put("followups", result[1]);

			returnList.add(userCallMap);
		}

		return returnList;
	}

}