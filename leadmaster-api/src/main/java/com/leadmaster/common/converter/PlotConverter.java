package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Plot;
import com.leadmaster.common.dto.PlotDTO;

@Component
public class PlotConverter {

	public static Plot getPlotByPlotDTO(PlotDTO plotDTO) {
		Plot plot = new Plot();
		plot.setId(plotDTO.getId());
		plot.setPlotName(plotDTO.getPlotName());
		plot.setStatus(plotDTO.getStatus());
		plot.setCreatedDate(plotDTO.getCreatedDate());
		plot.setCreatedBy(plotDTO.getCreatedBy());
		plot.setUpdatedDate(plotDTO.getUpdatedDate());
		plot.setUpdatedBy(plotDTO.getUpdatedBy());

		return plot;
	}

	public static PlotDTO getPlotDTOByPlot(Plot plot) {
		PlotDTO dto = new PlotDTO();

		dto.setId(plot.getId());
		dto.setPlotName(plot.getPlotName());
		dto.setStatus(plot.getStatus());
		dto.setCreatedDate(plot.getCreatedDate());
		dto.setCreatedBy(plot.getCreatedBy());
		dto.setUpdatedDate(plot.getUpdatedDate());
		dto.setUpdatedBy(plot.getUpdatedBy());

		return dto;
	}

}
