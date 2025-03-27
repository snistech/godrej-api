package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.PlotSize;
import com.leadmaster.common.dto.PlotSizeDTO;

public interface PlotSizeService {

	public void savePlotSize(PlotSizeDTO plotSizeDTO);

	public List<PlotSize> getAllPlotSizes(PlotSizeDTO plotSizeDTO);

	public PlotSize getPlotSizeById(PlotSizeDTO plotSizeDTO);

	public void updatePlotSize(PlotSizeDTO plotSizeDTO);

	public PlotSize deletePlotSize(PlotSizeDTO plotSizeDTO);

}
