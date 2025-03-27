package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.Plot;
import com.leadmaster.common.dto.PlotDTO;

public interface PlotDao {

	public Plot savePlot(PlotDTO plotDTO);

	public List<Plot> getAllPlot(PlotDTO plotDTO);

	public Plot getPlotById(Long id);

	public List<Plot> getPlotByName(String plotName);

}
