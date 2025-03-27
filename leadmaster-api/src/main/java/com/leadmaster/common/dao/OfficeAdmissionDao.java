package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.OfficeAdmission;
import com.leadmaster.common.dto.OfficeAdmissionDTO;

public interface OfficeAdmissionDao {

	public OfficeAdmission saveOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO);

	public List<OfficeAdmission> getAllOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO);

	public OfficeAdmission getOfficeAdmissionById(Long id);

	public List<Map<String, Object>> getAllOfficeAdmissions(OfficeAdmissionDTO officeAdmissionDTO);

}
