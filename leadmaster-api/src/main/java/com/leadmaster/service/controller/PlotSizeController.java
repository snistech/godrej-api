package com.leadmaster.service.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.domain.PlotSize;
import com.leadmaster.common.dto.PlotSizeDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.service.service.PlotSizeService;
import com.leadmaster.service.validator.PlotSizeValidator;

@RestController
@RequestMapping("/plotSize")
public class PlotSizeController {

	private static Logger LOGGER = LoggerFactory.getLogger(PlotSizeController.class);

	@Autowired
	PlotSizeService plotSizeService;

	@Autowired
	PlotSizeValidator plotSizeValidator;

	private LinkedHashMap<String, Object> returnMap;

	// amenities
	@RequestMapping(value = "/addPlotSize", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> addPlotSize(@RequestBody PlotSizeDTO plotSizeDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PlotSizeDTO.class.getName());
		plotSizeValidator.savePlotSize(plotSizeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		plotSizeService.savePlotSize(plotSizeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPlotSizes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPlotSizes(@RequestBody PlotSizeDTO plotSizeDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PlotSizeDTO.class.getName());
		plotSizeValidator.getAllPlotSizes(plotSizeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<PlotSize> plotSizes = plotSizeService.getAllPlotSizes(plotSizeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("plotSizes", plotSizes);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPlotSize", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPlotSize(@RequestBody PlotSizeDTO plotSizeDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PlotSizeDTO.class.getName());
		plotSizeValidator.getPlotSizeById(plotSizeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		PlotSize plotSizes = plotSizeService.getPlotSizeById(plotSizeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("plotSizes", plotSizes);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updatePlotSize", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updatePlotSize(@RequestBody PlotSizeDTO plotSizeDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PlotSizeDTO.class.getName());
		plotSizeValidator.updatePlotSize(plotSizeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		plotSizeService.updatePlotSize(plotSizeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(path = "/deletePlotSize", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> deletePlotSize(@RequestBody PlotSizeDTO CollegeDto,
			BindingResult result) {

		Map<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PlotSizeDTO.class.getName());
		plotSizeValidator.deletePlotSize(CollegeDto, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0) {
			throw new FieldException(list);
		}

		plotSizeService.deletePlotSize(CollegeDto);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@Bean
	public PlotSizeValidator getPlotSizeValidator() {
		return new PlotSizeValidator();
	}

}
