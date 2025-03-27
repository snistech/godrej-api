package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.PlotSize;
import com.leadmaster.common.dto.PlotSizeDTO;

@Component
public class PlotSizeConverter {

	public static PlotSize getPlotSizeByPlotSizeDTO(PlotSizeDTO plotSizeDTO) {
		PlotSize plotSize = new PlotSize();
		plotSize.setId(plotSizeDTO.getId());
		plotSize.setSize(plotSizeDTO.getSize());
		plotSize.setStatus(plotSizeDTO.getStatus());
		plotSize.setCreatedDate(plotSizeDTO.getCreatedDate());
		plotSize.setCreatedBy(plotSizeDTO.getCreatedBy());
		plotSize.setUpdatedDate(plotSizeDTO.getUpdatedDate());
		plotSize.setUpdatedBy(plotSizeDTO.getUpdatedBy());

		return plotSize;
	}

	public static PlotSizeDTO getPlotSizeDTOByPlotSize(PlotSize plotSize) {
		PlotSizeDTO dto = new PlotSizeDTO();

		dto.setId(plotSize.getId());
		dto.setSize(plotSize.getSize());
		dto.setStatus(plotSize.getStatus());
		dto.setCreatedDate(plotSize.getCreatedDate());
		dto.setCreatedBy(plotSize.getCreatedBy());
		dto.setUpdatedDate(plotSize.getUpdatedDate());
		dto.setUpdatedBy(plotSize.getUpdatedBy());

		return dto;
	}

}
