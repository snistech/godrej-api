package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.PlotConverter;
import com.leadmaster.common.dao.PlotDao;
import com.leadmaster.common.domain.Plot;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.PlotDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.PlotService;

@Service("PlotServiceImpl")
public class PlotServiceImpl implements PlotService {

	private static Logger LOGGER = LoggerFactory.getLogger(PlotServiceImpl.class);

	@Resource(name = "PlotDaoImpl")
	PlotDao plotDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void savePlot(PlotDTO plotDTO) {
		List<Role> roles = loginService.getAllUserRoles(plotDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save Plot Details.");

		// Check institute type already exists in db or not
		checkPlotName(plotDTO.getPlotName());

		plotDao.savePlot(plotDTO);
		LOGGER.info("Plot added successfully by " + plotDTO.getCreatedBy());
	}

	@Override
	public List<Plot> getAllPlots(PlotDTO plotDTO) {
		return plotDao.getAllPlot(plotDTO);
	}

	@Override
	public Plot getPlotById(PlotDTO plotDTO) {
		return plotDao.getPlotById(plotDTO.getId());
	}

	@Override
	public void updatePlot(PlotDTO plotDTO) {
		List<Role> roles = loginService.getAllUserRoles(plotDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		Plot plots = plotDao.getPlotById(plotDTO.getId());
		PlotDTO dbCollegeDTO = PlotConverter.getPlotDTOByPlot(plots);

		if (null != plotDTO.getPlotName() && !plotDTO.getPlotName().equals(dbCollegeDTO.getPlotName())) {

			// Check institute type already exists in db or not
			checkPlotName(plotDTO.getPlotName());
			dbCollegeDTO.setPlotName(plotDTO.getPlotName());
		}

		dbCollegeDTO.setUpdatedBy(plotDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(plotDTO.getUpdatedDate());
		plotDao.savePlot(dbCollegeDTO);
		LOGGER.info("Plot " + plotDTO.getId() + " updated successfully by " + plotDTO.getUpdatedBy());
	}

	private void checkPlotName(String plotName) {
		List<Plot> dbCollege = plotDao.getPlotByName(plotName);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Plot already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public Plot deletePlot(PlotDTO plotDTO) {
		Plot dbCollege = plotDao.getPlotById(plotDTO.getId());
		PlotDTO dbCollegeDTO = PlotConverter.getPlotDTOByPlot(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return plotDao.savePlot(dbCollegeDTO);

	}

}
