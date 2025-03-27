package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.PlotSizeConverter;
import com.leadmaster.common.dao.PlotSizeDao;
import com.leadmaster.common.domain.PlotSize;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.PlotSizeDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.PlotSizeService;

@Service("PlotSizeServiceImpl")
public class PlotSizeServiceImpl implements PlotSizeService {

	private static Logger LOGGER = LoggerFactory.getLogger(PlotSizeServiceImpl.class);

	@Resource(name = "PlotSizeDaoImpl")
	PlotSizeDao plotSizeDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void savePlotSize(PlotSizeDTO plotSizeDTO) {
		List<Role> roles = loginService.getAllUserRoles(plotSizeDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save PlotSize Details.");

		// Check institute type already exists in db or not
		checkPlotSize(plotSizeDTO.getSize());

		plotSizeDao.savePlotSize(plotSizeDTO);
		LOGGER.info("PlotSize added successfully by " + plotSizeDTO.getCreatedBy());
	}

	@Override
	public List<PlotSize> getAllPlotSizes(PlotSizeDTO plotSizeDTO) {
		return plotSizeDao.getAllPlotSize(plotSizeDTO);
	}

	@Override
	public PlotSize getPlotSizeById(PlotSizeDTO plotSizeDTO) {
		return plotSizeDao.getPlotSizeById(plotSizeDTO.getId());
	}

	@Override
	public void updatePlotSize(PlotSizeDTO plotSizeDTO) {
		List<Role> roles = loginService.getAllUserRoles(plotSizeDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update PlotSizes Details.");

		PlotSize plotSizes = plotSizeDao.getPlotSizeById(plotSizeDTO.getId());
		PlotSizeDTO dbPlotSizeDTO = PlotSizeConverter.getPlotSizeDTOByPlotSize(plotSizes);

		if (null != plotSizeDTO.getSize() && !plotSizeDTO.getSize().equals(dbPlotSizeDTO.getSize())) {

			// Check institute type already exists in db or not
			checkPlotSize(plotSizeDTO.getSize());
			dbPlotSizeDTO.setSize(plotSizeDTO.getSize());
		}

		dbPlotSizeDTO.setUpdatedBy(plotSizeDTO.getUpdatedBy());
		dbPlotSizeDTO.setUpdatedDate(plotSizeDTO.getUpdatedDate());
		plotSizeDao.savePlotSize(dbPlotSizeDTO);
		LOGGER.info("PlotSize " + plotSizeDTO.getId() + " updated successfully by " + plotSizeDTO.getUpdatedBy());
	}

	private void checkPlotSize(String size) {
		List<PlotSize> dbPlotSize = plotSizeDao.getPlotBySize(size);
		if (null != dbPlotSize && dbPlotSize.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "PlotSize already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public PlotSize deletePlotSize(PlotSizeDTO plotSizeDTO) {
		PlotSize dbPlotSize = plotSizeDao.getPlotSizeById(plotSizeDTO.getId());
		PlotSizeDTO dbPlotSizeDTO = PlotSizeConverter.getPlotSizeDTOByPlotSize(dbPlotSize);

		dbPlotSizeDTO.setStatus(Constant.STATUS_INACTIVE);
		return plotSizeDao.savePlotSize(dbPlotSizeDTO);

	}

}
