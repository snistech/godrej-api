package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.dto.LeadTrackDTO;

public interface LeadTrackService {

	public void saveLeadTrack(LeadTrackDTO leadTrackDTO);

	public List<Map<String, Object>> getAllLeadTracks(LeadTrackDTO leadTrackDTO);

}
