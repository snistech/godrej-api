package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.PlotSize;
import com.leadmaster.common.dto.PlotSizeDTO;

public interface PlotSizeDao {

	public PlotSize savePlotSize(PlotSizeDTO plotDTO);

	public List<PlotSize> getAllPlotSize(PlotSizeDTO plotDTO);

	public PlotSize getPlotSizeById(Long id);

	public List<PlotSize> getPlotBySize(String size);

}
