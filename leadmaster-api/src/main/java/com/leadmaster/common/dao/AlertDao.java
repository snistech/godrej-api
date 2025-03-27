package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.Alert;
import com.leadmaster.common.dto.AlertDTO;

public interface AlertDao {

	public Alert saveAlert(AlertDTO alertDTO);

	public List<Alert> getAllAlert(AlertDTO alertDTO);

	public Alert getAlertById(Long id);

}
