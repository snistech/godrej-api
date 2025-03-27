package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Alert;
import com.leadmaster.common.dto.AlertDTO;

@Component
public class AlertConverter {

	public static Alert getAlertByAlertDTO(AlertDTO alertDTO) {
		Alert alert = new Alert();
		alert.setId(alertDTO.getId());
		alert.setAlertText(alertDTO.getAlertText());
		alert.setBranch(alertDTO.getBranch());
		alert.setRole(alertDTO.getRole());
		alert.setAlertStatus(alertDTO.getAlertStatus());
		alert.setStatus(alertDTO.getStatus());
		alert.setCreatedDate(alertDTO.getCreatedDate());
		alert.setCreatedBy(alertDTO.getCreatedBy());
		alert.setUpdatedDate(alertDTO.getUpdatedDate());
		alert.setUpdatedBy(alertDTO.getUpdatedBy());

		return alert;
	}

	public static AlertDTO getAlertDTOByAlert(Alert alert) {
		AlertDTO dto = new AlertDTO();

		dto.setId(alert.getId());
		dto.setAlertText(alert.getAlertText());
		dto.setBranch(alert.getBranch());
		dto.setRole(alert.getRole());
		dto.setAlertStatus(alert.getAlertStatus());
		dto.setStatus(alert.getStatus());
		dto.setCreatedDate(alert.getCreatedDate());
		dto.setCreatedBy(alert.getCreatedBy());
		dto.setUpdatedDate(alert.getUpdatedDate());
		dto.setUpdatedBy(alert.getUpdatedBy());

		return dto;
	}

}
