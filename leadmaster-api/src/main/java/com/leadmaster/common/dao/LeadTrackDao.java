package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.LeadTrack;
import com.leadmaster.common.dto.LeadTrackDTO;

public interface LeadTrackDao {

	public LeadTrack saveLeadTrack(LeadTrackDTO leadTrackDTO);

	public List<Map<String, Object>> getAllLeadTracks(LeadTrackDTO leadTrackDTO);

	public List<LeadTrack> getLeadTrackByLeadId(Long leadId);

}
