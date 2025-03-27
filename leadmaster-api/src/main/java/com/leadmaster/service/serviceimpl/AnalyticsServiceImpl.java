package com.leadmaster.service.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.dao.AnalyticsDao;
import com.leadmaster.common.dto.AnalyticsDTO;
import com.leadmaster.service.service.AnalyticsService;

@Service("AnalyticsServiceImpl")
public class AnalyticsServiceImpl implements AnalyticsService {

	private static Logger LOGGER = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

	private Map<String, Integer> lastAssignedUserIndex = new HashMap<>();

	@Resource(name = "AnalyticsDaoImpl")
	AnalyticsDao analyticsDao;

	@Override
	public List<Map<String, Object>> getConversionAnalytics(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getConversionAnalytics(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getFailureAnalytics(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getFailureAnalytics(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getDataCoverage(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getDataCoverage(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getTeamCalls(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getTeamCalls(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getCollegeAdmissions(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getCollegeAdmissions(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getGraphData(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getGraphData(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getCurrentLeadTracking(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getCurrentLeadTracking(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getCallAverageDifference(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getCallAverageDifference(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getGetMyCollegeAnalytics(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getGetMyCollegeAnalytics(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getIndividualFollowups(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getIndividualFollowups(analyticsDTO);
	}

	@Override
	public List<Map<String, Object>> getLevelOneFollowups(AnalyticsDTO analyticsDTO) {
		return analyticsDao.getLevelOneFollowups(analyticsDTO);
	}

}
