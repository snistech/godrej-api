package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.OfficeAdmission;
import com.leadmaster.common.dto.OfficeAdmissionDTO;

public interface OfficeAdmissionService {

	public void saveOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO);

	public List<OfficeAdmission> getAllOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO);

	public OfficeAdmission getOfficeAdmissionById(OfficeAdmissionDTO officeAdmissionDTO);

	public void updateOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO);

	public OfficeAdmission deleteOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO);

	public List<Map<String, Object>> getAllOfficeAdmissions(OfficeAdmissionDTO officeAdmissionDTO);

}
