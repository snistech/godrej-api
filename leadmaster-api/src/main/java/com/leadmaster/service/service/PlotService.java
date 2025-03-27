package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.Plot;
import com.leadmaster.common.dto.PlotDTO;

public interface PlotService {

	public void savePlot(PlotDTO plotDTO);

	public List<Plot> getAllPlots(PlotDTO plotDTO);

	public Plot getPlotById(PlotDTO plotDTO);

	public void updatePlot(PlotDTO plotDTO);

	public Plot deletePlot(PlotDTO plotDTO);

}
