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
import com.leadmaster.common.domain.PropertyValues;
import com.leadmaster.common.dto.PropertyValuesDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.service.service.PropertyValuesService;
import com.leadmaster.service.validator.PropertyValueValidator;

@RestController
@RequestMapping("/propertyValue")
public class PropertyValueController {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertyValueController.class);

	@Autowired
	PropertyValuesService propertyValuesService;

	@Autowired
	PropertyValueValidator propertyValueValidator;

	private LinkedHashMap<String, Object> returnMap;

	@RequestMapping(value = "/addPropertyValue", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> addPropertyValue(
			@RequestBody PropertyValuesDTO propertyValuesDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.savePropertyValue(propertyValuesDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		propertyValuesService.savePropertyValue(propertyValuesDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getAllPropertyValues", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getAllPropertyValues(
			@RequestBody PropertyValuesDTO propertyValuesDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.getAllPropertyValues(propertyValuesDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<PropertyValues> propertyValues = propertyValuesService.getAllPropertyValues(propertyValuesDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("propertyValues", propertyValues);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPropertyValue", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPropertyValue(
			@RequestBody PropertyValuesDTO propertyValuesDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.getPropertyValueById(propertyValuesDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		PropertyValues propertyValues = propertyValuesService.getPropertyValueById(propertyValuesDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("propertyValue", propertyValues);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updatePropertyValue", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updatePropertyValue(
			@RequestBody PropertyValuesDTO propertyValuesDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.updatePropertyValue(propertyValuesDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		propertyValuesService.updatePropertyValue(propertyValuesDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(path = "/deletePropertyValue", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> deletePropertyValue(
			@RequestBody PropertyValuesDTO PropertyValueDto, BindingResult result) {

		Map<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.deletePropertyValue(PropertyValueDto, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0) {
			throw new FieldException(list);
		}

		propertyValuesService.deletePropertyValue(PropertyValueDto);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPropertyValueData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPropertyValueData(
			@RequestBody PropertyValuesDTO propertyValuesDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.getPropertyValueData(propertyValuesDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<PropertyValues> propertyValues = propertyValuesService.getPropertyValueData(propertyValuesDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("propertyValues", propertyValues);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPropertyValues", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPropertyValues(
			@RequestBody PropertyValuesDTO propertyValuesDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyValuesDTO.class.getName());
		propertyValueValidator.getPropertyValues(propertyValuesDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<Map<String, Object>> propertyValues = propertyValuesService.getPropertyValues(propertyValuesDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("propertyValues", propertyValues);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@Bean
	public PropertyValueValidator getPropertyValueValidator() {
		return new PropertyValueValidator();
	}

}
