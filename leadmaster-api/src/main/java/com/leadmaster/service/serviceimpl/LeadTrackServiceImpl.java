package com.leadmaster.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.dao.LeadTrackDao;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.LeadTrackDTO;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.LeadTrackService;

@Service("LeadTrackServiceImpl")
public class LeadTrackServiceImpl implements LeadTrackService {

	private static Logger LOGGER = LoggerFactory.getLogger(LeadTrackServiceImpl.class);

	@Resource(name = "LeadTrackDaoImpl")
	LeadTrackDao leadTrackDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveLeadTrack(LeadTrackDTO leadTrackDTO) {
//		List<Role> roles = loginService.getAllUserRoles(leadTrackDTO.getCreatedBy());
//		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
//				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
//		if (!adminAccess)
//			throw new UnAuthorizedException("LogedIn User does't have permission to save LeadTrack Details.");

		leadTrackDao.saveLeadTrack(leadTrackDTO);
		LOGGER.info("LeadTrack added successfully by " + leadTrackDTO.getCreatedBy());
	}

	@Override
	public List<Map<String, Object>> getAllLeadTracks(LeadTrackDTO leadTrackDTO) {
		return leadTrackDao.getAllLeadTracks(leadTrackDTO);
	}

}
