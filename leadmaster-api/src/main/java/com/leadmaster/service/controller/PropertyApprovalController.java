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
import com.leadmaster.common.domain.PropertyApproval;
import com.leadmaster.common.dto.PropertyApprovalDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.service.service.PropertyApprovalService;
import com.leadmaster.service.validator.PropertyApprovalValidator;

@RestController
@RequestMapping("/propertyApproval")
public class PropertyApprovalController {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertyApprovalController.class);

	@Autowired
	PropertyApprovalService propertyApprovalService;

	@Autowired
	PropertyApprovalValidator propertyApprovalValidator;

	private LinkedHashMap<String, Object> returnMap;

	// amenities
	@RequestMapping(value = "/addPropertyApproval", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> addPropertyApproval(
			@RequestBody PropertyApprovalDTO propertyApprovalDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyApprovalDTO.class.getName());
		propertyApprovalValidator.savePropertyApproval(propertyApprovalDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		propertyApprovalService.savePropertyApproval(propertyApprovalDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPropertyApprovals", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPropertyApprovals(
			@RequestBody PropertyApprovalDTO propertyApprovalDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyApprovalDTO.class.getName());
		propertyApprovalValidator.getAllPropertyApprovals(propertyApprovalDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<PropertyApproval> propertyApprovals = propertyApprovalService.getAllPropertyApprovals(propertyApprovalDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("propertyApprovals", propertyApprovals);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getPropertyApproval", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getPropertyApproval(
			@RequestBody PropertyApprovalDTO propertyApprovalDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyApprovalDTO.class.getName());
		propertyApprovalValidator.getPropertyApprovalById(propertyApprovalDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		PropertyApproval propertyApprovals = propertyApprovalService.getPropertyApprovalById(propertyApprovalDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("propertyApprovals", propertyApprovals);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updatePropertyApproval", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updatePropertyApproval(
			@RequestBody PropertyApprovalDTO propertyApprovalDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyApprovalDTO.class.getName());
		propertyApprovalValidator.updatePropertyApproval(propertyApprovalDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		propertyApprovalService.updatePropertyApproval(propertyApprovalDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(path = "/deletePropertyApproval", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> deletePropertyApproval(
			@RequestBody PropertyApprovalDTO CollegeDto, BindingResult result) {

		Map<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, PropertyApprovalDTO.class.getName());
		propertyApprovalValidator.deletePropertyApproval(CollegeDto, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0) {
			throw new FieldException(list);
		}

		propertyApprovalService.deletePropertyApproval(CollegeDto);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@Bean
	public PropertyApprovalValidator getPropertyApprovalValidator() {
		return new PropertyApprovalValidator();
	}

}
