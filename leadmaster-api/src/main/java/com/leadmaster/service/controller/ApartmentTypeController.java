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
import com.leadmaster.common.domain.ApartmentType;
import com.leadmaster.common.dto.ApartmentTypeDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.service.service.ApartmentTypeService;
import com.leadmaster.service.validator.ApartmentTypeValidator;

@RestController
@RequestMapping("/apartmentType")
public class ApartmentTypeController {

	private static Logger LOGGER = LoggerFactory.getLogger(ApartmentTypeController.class);

	@Autowired
	ApartmentTypeService apartmentTypeService;

	@Autowired
	ApartmentTypeValidator apartmentTypeValidator;

	private LinkedHashMap<String, Object> returnMap;

	// apartmentType
	@RequestMapping(value = "/addApartmentType", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> addApartmentType(
			@RequestBody ApartmentTypeDTO apartmentTypeDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, ApartmentTypeDTO.class.getName());
		apartmentTypeValidator.saveApartmentType(apartmentTypeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		apartmentTypeService.saveApartmentType(apartmentTypeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getApartmentTypes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getApartmentTypes(
			@RequestBody ApartmentTypeDTO apartmentTypeDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, ApartmentTypeDTO.class.getName());
		apartmentTypeValidator.getAllApartmentTypes(apartmentTypeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<ApartmentType> apartmentTypes = apartmentTypeService.getAllApartmentTypes(apartmentTypeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("apartmentType", apartmentTypes);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getApartmentType", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getApartmentType(
			@RequestBody ApartmentTypeDTO apartmentTypeDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, ApartmentTypeDTO.class.getName());
		apartmentTypeValidator.getApartmentTypeById(apartmentTypeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		ApartmentType apartmentTypes = apartmentTypeService.getApartmentTypeById(apartmentTypeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("apartmentType", apartmentTypes);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updateApartmentType", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updateApartmentType(
			@RequestBody ApartmentTypeDTO apartmentTypeDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, ApartmentTypeDTO.class.getName());
		apartmentTypeValidator.updateApartmentType(apartmentTypeDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		apartmentTypeService.updateApartmentType(apartmentTypeDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(path = "/deleteApartmentType", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> deleteApartmentType(@RequestBody ApartmentTypeDTO CollegeDto,
			BindingResult result) {

		Map<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, ApartmentTypeDTO.class.getName());
		apartmentTypeValidator.deleteApartmentType(CollegeDto, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0) {
			throw new FieldException(list);
		}

		apartmentTypeService.deleteApartmentType(CollegeDto);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@Bean
	public ApartmentTypeValidator getApartmentTypeValidator() {
		return new ApartmentTypeValidator();
	}

}
