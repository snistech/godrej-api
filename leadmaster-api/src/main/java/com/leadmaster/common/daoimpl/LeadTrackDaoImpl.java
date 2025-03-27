package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.LeadTrackConverter;
import com.leadmaster.common.dao.LeadTrackDao;
import com.leadmaster.common.domain.LeadTrack;
import com.leadmaster.common.dto.LeadTrackDTO;
import com.leadmaster.common.repository.LeadTrackRepository;

@Transactional
@Service("LeadTrackDaoImpl")
public class LeadTrackDaoImpl implements LeadTrackDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private LeadTrackRepository leadTrackRepository;

	@Override
	public LeadTrack saveLeadTrack(LeadTrackDTO leadTrackDTO) {
		LeadTrack leadTracks = LeadTrackConverter.getLeadTrackByLeadTrackDTO(leadTrackDTO);
		return leadTrackRepository.save(leadTracks);
	}

	@Override
	public List<Map<String, Object>> getAllLeadTracks(LeadTrackDTO leadTrackDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT a.id, a.lead_id, a.followup_date, a.next_followup_date, a.lead_status, a.remarks, u.name "
						+ "FROM lead_track a " + "INNER JOIN users u ON a.created_by = u.id " + "WHERE 1=1");

		if (null != leadTrackDTO.getLeadId())
			sqlQuery.append(" AND a.lead_id = :leadId");

		sqlQuery.append(" ORDER BY a.id DESC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (null != leadTrackDTO.getLeadId())
			query.setParameter("leadId", leadTrackDTO.getLeadId());

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> leadTrackMap = new LinkedHashMap<>();
			leadTrackMap.put("id", result[0]);
			leadTrackMap.put("leadId", result[1]);
			leadTrackMap.put("followupDate", result[2]);
			leadTrackMap.put("nextFollowupDate", result[3]);
			leadTrackMap.put("leadStatus", result[4]);
			leadTrackMap.put("remarks", result[5]);
			leadTrackMap.put("updatedBy", result[6]);

			returnList.add(leadTrackMap);
		}

		return returnList;
	}

	@Override
	public List<LeadTrack> getLeadTrackByLeadId(Long leadId) {
		return leadTrackRepository.getLeadTrackByLeadId(leadId);
	}

}
