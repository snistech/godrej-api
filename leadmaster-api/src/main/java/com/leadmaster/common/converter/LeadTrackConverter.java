package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.LeadTrack;
import com.leadmaster.common.dto.LeadTrackDTO;

@Component
public class LeadTrackConverter {

	public static LeadTrack getLeadTrackByLeadTrackDTO(LeadTrackDTO leadTrackDTO) {
		LeadTrack leadTrack = new LeadTrack();
		leadTrack.setId(leadTrackDTO.getId());
		leadTrack.setLeadId(leadTrackDTO.getLeadId());
		leadTrack.setFollowupDate(leadTrackDTO.getFollowupDate());
		leadTrack.setNextFollowupDate(leadTrackDTO.getNextFollowupDate());
		leadTrack.setLeadStatus(leadTrackDTO.getLeadStatus());
		leadTrack.setRemarks(leadTrackDTO.getRemarks());
		leadTrack.setStatus(leadTrackDTO.getStatus());
		leadTrack.setCreatedDate(leadTrackDTO.getCreatedDate());
		leadTrack.setCreatedBy(leadTrackDTO.getCreatedBy());
		leadTrack.setUpdatedDate(leadTrackDTO.getUpdatedDate());
		leadTrack.setUpdatedBy(leadTrackDTO.getUpdatedBy());

		return leadTrack;
	}

	public static LeadTrackDTO getLeadTrackDTOByLeadTrack(LeadTrack leadTrack) {
		LeadTrackDTO dto = new LeadTrackDTO();

		dto.setId(leadTrack.getId());
		dto.setLeadId(leadTrack.getLeadId());
		dto.setFollowupDate(leadTrack.getFollowupDate());
		dto.setNextFollowupDate(leadTrack.getNextFollowupDate());
		dto.setLeadStatus(leadTrack.getLeadStatus());
		dto.setRemarks(leadTrack.getRemarks());
		dto.setStatus(leadTrack.getStatus());
		dto.setCreatedDate(leadTrack.getCreatedDate());
		dto.setCreatedBy(leadTrack.getCreatedBy());
		dto.setUpdatedDate(leadTrack.getUpdatedDate());
		dto.setUpdatedBy(leadTrack.getUpdatedBy());

		return dto;
	}

}
