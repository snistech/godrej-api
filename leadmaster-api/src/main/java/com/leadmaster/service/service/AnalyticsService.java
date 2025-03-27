package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.dto.AnalyticsDTO;

public interface AnalyticsService {

	public List<Map<String, Object>> getConversionAnalytics(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getFailureAnalytics(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getDataCoverage(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getTeamCalls(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getCollegeAdmissions(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getGraphData(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getCurrentLeadTracking(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getCallAverageDifference(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getGetMyCollegeAnalytics(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getIndividualFollowups(AnalyticsDTO analyticsDTO);

	public List<Map<String, Object>> getLevelOneFollowups(AnalyticsDTO analyticsDTO);

}
