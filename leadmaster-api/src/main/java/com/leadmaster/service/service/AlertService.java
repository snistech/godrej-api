package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.Alert;
import com.leadmaster.common.dto.AlertDTO;

public interface AlertService {

	public void saveAlert(AlertDTO alertDTO);

	public List<Alert> getAllAlerts(AlertDTO alertDTO);

	public Alert getAlertById(AlertDTO alertDTO);

	public void updateAlert(AlertDTO alertDTO);

	public Alert deleteAlert(AlertDTO alertDTO);

}
