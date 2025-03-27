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
import com.leadmaster.common.domain.OfficeAdmission;
import com.leadmaster.common.dto.LeadDTO;
import com.leadmaster.common.dto.OfficeAdmissionDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.service.service.OfficeAdmissionService;
import com.leadmaster.service.validator.OfficeAdmissionValidator;

@RestController
@RequestMapping("/officeAdmission")
public class OfficeAdmissionController {

	private static Logger LOGGER = LoggerFactory.getLogger(OfficeAdmissionController.class);

	@Autowired
	OfficeAdmissionService officeAdmissionService;

	@Autowired
	OfficeAdmissionValidator officeAdmissionValidator;

	private LinkedHashMap<String, Object> returnMap;

	// amenities
	@RequestMapping(value = "/addOfficeAdmission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> addOfficeAdmission(
			@RequestBody OfficeAdmissionDTO officeAdmissionDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, OfficeAdmissionDTO.class.getName());
		officeAdmissionValidator.saveOfficeAdmission(officeAdmissionDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		officeAdmissionService.saveOfficeAdmission(officeAdmissionDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getOfficeAdmissions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getOfficeAdmissions(
			@RequestBody OfficeAdmissionDTO officeAdmissionDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, OfficeAdmissionDTO.class.getName());
		officeAdmissionValidator.getAllOfficeAdmission(officeAdmissionDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<OfficeAdmission> officeAdmissions = officeAdmissionService.getAllOfficeAdmission(officeAdmissionDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("officeAdmissions", officeAdmissions);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getOfficeAdmission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getOfficeAdmission(
			@RequestBody OfficeAdmissionDTO officeAdmissionDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, OfficeAdmissionDTO.class.getName());
		officeAdmissionValidator.getOfficeAdmissionById(officeAdmissionDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		OfficeAdmission officeAdmissions = officeAdmissionService.getOfficeAdmissionById(officeAdmissionDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("officeAdmission", officeAdmissions);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updateOfficeAdmission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updateOfficeAdmission(
			@RequestBody OfficeAdmissionDTO officeAdmissionDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, OfficeAdmissionDTO.class.getName());
		officeAdmissionValidator.updateOfficeAdmission(officeAdmissionDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		officeAdmissionService.updateOfficeAdmission(officeAdmissionDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

//	@RequestMapping(path = "/deleteOfficeAdmission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//	public ResponseEntity<LinkedHashMap<String, Object>> deleteOfficeAdmission(
//			@RequestBody OfficeAdmissionDTO OfficeAdmissionDto, BindingResult result) {
//
//		Map<String, String> map = new HashMap<String, String>();
//		MapBindingResult err = new MapBindingResult(map, OfficeAdmissionDTO.class.getName());
//		officeAdmissionValidator.deleteOfficeAdmission(OfficeAdmissionDto, err);
//		List<ObjectError> list = err.getAllErrors();
//		if (list.size() > 0) {
//			throw new FieldException(list);
//		}
//
//		officeAdmissionService.deleteOfficeAdmission(OfficeAdmissionDto);
//		returnMap = new LinkedHashMap<String, Object>();
//		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
//		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
//		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
//	}

	@RequestMapping(value = "/getAllOfficeAdmissions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getAllOfficeAdmissions(
			@RequestBody OfficeAdmissionDTO officeAdmissionDTO, HttpServletRequest request, BindingResult result)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, LeadDTO.class.getName());
		officeAdmissionValidator.getAllOfficeAdmissions(officeAdmissionDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<Map<String, Object>> leadMap = officeAdmissionService.getAllOfficeAdmissions(officeAdmissionDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("officeAdmissions", leadMap);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@Bean
	public OfficeAdmissionValidator getOfficeAdmissionValidator() {
		return new OfficeAdmissionValidator();
	}

}
