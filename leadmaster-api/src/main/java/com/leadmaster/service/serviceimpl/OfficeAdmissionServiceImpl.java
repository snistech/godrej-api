package com.leadmaster.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.OfficeAdmissionConverter;
import com.leadmaster.common.dao.OfficeAdmissionDao;
import com.leadmaster.common.domain.OfficeAdmission;
import com.leadmaster.common.dto.OfficeAdmissionDTO;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.service.service.OfficeAdmissionService;

@Service("OfficeAdmissionServiceImpl")
public class OfficeAdmissionServiceImpl implements OfficeAdmissionService {

	private static Logger LOGGER = LoggerFactory.getLogger(OfficeAdmissionServiceImpl.class);

	@Resource(name = "OfficeAdmissionDaoImpl")
	OfficeAdmissionDao officeAdmissionDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO) {

		officeAdmissionDao.saveOfficeAdmission(officeAdmissionDTO);
		LOGGER.info("OfficeAdmission added successfully by " + officeAdmissionDTO.getCreatedBy());
	}

	@Override
	public List<OfficeAdmission> getAllOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO) {
		return officeAdmissionDao.getAllOfficeAdmission(officeAdmissionDTO);
	}

	@Override
	public OfficeAdmission getOfficeAdmissionById(OfficeAdmissionDTO officeAdmissionDTO) {
		return officeAdmissionDao.getOfficeAdmissionById(officeAdmissionDTO.getId());
	}

	@Override
	public void updateOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO) {

		OfficeAdmission officeAdmissions = officeAdmissionDao.getOfficeAdmissionById(officeAdmissionDTO.getId());
		OfficeAdmissionDTO dbOfficeAdmissionDTO = OfficeAdmissionConverter
				.getOfficeAdmissionDTOByOfficeAdmission(officeAdmissions);

		if (null != officeAdmissionDTO.getAlternativeNumber())
			dbOfficeAdmissionDTO.setAlternativeNumber(officeAdmissionDTO.getAlternativeNumber());

		if (null != officeAdmissionDTO.getEmail())
			dbOfficeAdmissionDTO.setEmail(officeAdmissionDTO.getEmail());

		if (null != officeAdmissionDTO.getAdharProof())
			dbOfficeAdmissionDTO.setAdharProof(officeAdmissionDTO.getAdharProof());

		if (null != officeAdmissionDTO.getPaidFees())
			dbOfficeAdmissionDTO.setPaidFees(officeAdmissionDTO.getPaidFees());

		if (null != officeAdmissionDTO.getPaymentStatus())
			dbOfficeAdmissionDTO.setPaymentStatus(officeAdmissionDTO.getPaymentStatus());

		if (null != officeAdmissionDTO.getAdmissionStatus())
			dbOfficeAdmissionDTO.setAdmissionStatus(officeAdmissionDTO.getAdmissionStatus());

		if (null != officeAdmissionDTO.getApprovalStatus())
			dbOfficeAdmissionDTO.setApprovalStatus(officeAdmissionDTO.getApprovalStatus());

		if (null != officeAdmissionDTO.getRemarks())
			dbOfficeAdmissionDTO.setRemarks(officeAdmissionDTO.getRemarks());

		dbOfficeAdmissionDTO.setUpdatedBy(officeAdmissionDTO.getUpdatedBy());
		dbOfficeAdmissionDTO.setUpdatedDate(officeAdmissionDTO.getUpdatedDate());
		officeAdmissionDao.saveOfficeAdmission(dbOfficeAdmissionDTO);
		LOGGER.info("OfficeAdmission " + officeAdmissionDTO.getId() + " updated successfully by "
				+ officeAdmissionDTO.getUpdatedBy());
	}

	@Override
	public OfficeAdmission deleteOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO) {
		OfficeAdmission dbOfficeAdmission = officeAdmissionDao.getOfficeAdmissionById(officeAdmissionDTO.getId());
		OfficeAdmissionDTO dbOfficeAdmissionDTO = OfficeAdmissionConverter
				.getOfficeAdmissionDTOByOfficeAdmission(dbOfficeAdmission);

		dbOfficeAdmissionDTO.setStatus(Constant.STATUS_INACTIVE);
		return officeAdmissionDao.saveOfficeAdmission(dbOfficeAdmissionDTO);

	}

	@Override
	public List<Map<String, Object>> getAllOfficeAdmissions(OfficeAdmissionDTO officeAdmissionDTO) {
		return officeAdmissionDao.getAllOfficeAdmissions(officeAdmissionDTO);
	}

}
