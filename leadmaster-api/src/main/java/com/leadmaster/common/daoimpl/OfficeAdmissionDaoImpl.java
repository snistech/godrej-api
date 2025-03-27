package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.OfficeAdmissionConverter;
import com.leadmaster.common.dao.OfficeAdmissionDao;
import com.leadmaster.common.domain.OfficeAdmission;
import com.leadmaster.common.dto.OfficeAdmissionDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.OfficeAdmissionRepository;

@Transactional
@Service("OfficeAdmissionDaoImpl")
public class OfficeAdmissionDaoImpl implements OfficeAdmissionDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private OfficeAdmissionRepository officeAdmissionRepository;

	@Override
	public OfficeAdmission saveOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO) {
		OfficeAdmission officeAdmissions = OfficeAdmissionConverter
				.getOfficeAdmissionByOfficeAdmissionDTO(officeAdmissionDTO);
		return officeAdmissionRepository.save(officeAdmissions);
	}

	@Override
	public List<OfficeAdmission> getAllOfficeAdmission(OfficeAdmissionDTO officeAdmissionDTO) {
		List<OfficeAdmission> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from OfficeAdmission a where 1=1");

		if (null != officeAdmissionDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != officeAdmissionDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");

		sqlQuery.append(" ORDER BY a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != officeAdmissionDTO.getId())
			query.setParameter("id", officeAdmissionDTO.getId());
		if (null != officeAdmissionDTO.getStatus())
			query.setParameter("status", officeAdmissionDTO.getStatus());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public OfficeAdmission getOfficeAdmissionById(Long id) {
		Optional<OfficeAdmission> officeAdmissions = officeAdmissionRepository.findById(id);
		if (!officeAdmissions.isPresent())
			throw new ResourceNotFoundException("The officeAdmission is not found in the system. id:" + id);
		return officeAdmissions.get();
	}

	@Override
	public List<Map<String, Object>> getAllOfficeAdmissions(OfficeAdmissionDTO officeAdmissionDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT a.id, a.full_name, a.phone_number, a.father_name, a.admitted_clg, a.admitted_date, "
						+ "a.total_fees, a.paid_fees, a.payment_status, u.name AS created_by_name "
						+ "FROM office_admission a " + "LEFT JOIN users u ON a.created_by = u.id " + "WHERE 1=1");

		if (null != officeAdmissionDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != officeAdmissionDTO.getFullName())
			sqlQuery.append(" AND a.full_name = :fullName");
		if (null != officeAdmissionDTO.getPhoneNumber())
			sqlQuery.append(" AND a.phone_number = :phoneNumber");
		if (null != officeAdmissionDTO.getCourse())
			sqlQuery.append(" AND a.course = :course");
		if (null != officeAdmissionDTO.getAdmittedDate())
			sqlQuery.append(" AND DATE(a.admitted_date) = :admittedDate");
		if (null != officeAdmissionDTO.getAdmittedClg())
			sqlQuery.append(" AND a.admitted_clg = :admittedClg");
		if (null != officeAdmissionDTO.getPaymentStatus())
			sqlQuery.append(" AND a.payment_status = :paymentStatus");
		if (null != officeAdmissionDTO.getCreatedBy())
			sqlQuery.append(" AND a.created_by = :createdBy");
		if (officeAdmissionDTO.getApprovalStatus() != null && !officeAdmissionDTO.getApprovalStatus().isEmpty()) {
			String[] approvalStatuses = officeAdmissionDTO.getApprovalStatus().split(",\\s*");
			sqlQuery.append(" AND a.approval_status IN (:approvalStatuses)");
		}

		sqlQuery.append(" ORDER BY a.id DESC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (null != officeAdmissionDTO.getId())
			query.setParameter("id", officeAdmissionDTO.getId());
		if (null != officeAdmissionDTO.getFullName())
			query.setParameter("fullName", officeAdmissionDTO.getFullName());
		if (null != officeAdmissionDTO.getPhoneNumber())
			query.setParameter("phoneNumber", officeAdmissionDTO.getPhoneNumber());
		if (null != officeAdmissionDTO.getCourse())
			query.setParameter("course", officeAdmissionDTO.getCourse());
		if (null != officeAdmissionDTO.getAdmittedDate())
			query.setParameter("admittedDate", officeAdmissionDTO.getAdmittedDate());
		if (null != officeAdmissionDTO.getAdmittedClg())
			query.setParameter("admittedClg", officeAdmissionDTO.getAdmittedClg());
		if (null != officeAdmissionDTO.getPaymentStatus())
			query.setParameter("paymentStatus", officeAdmissionDTO.getPaymentStatus());
		if (null != officeAdmissionDTO.getCreatedBy())
			query.setParameter("createdBy", officeAdmissionDTO.getCreatedBy());
		if (officeAdmissionDTO.getApprovalStatus() != null && !officeAdmissionDTO.getApprovalStatus().isEmpty()) {
			String[] approvalStatuses = officeAdmissionDTO.getApprovalStatus().split(",\\s*");
			query.setParameter("approvalStatuses", Arrays.asList(approvalStatuses));
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> officeAdmissionMap = new LinkedHashMap<>();
			officeAdmissionMap.put("id", result[0]);
			officeAdmissionMap.put("fullName", result[1]);
			officeAdmissionMap.put("phoneNumber", result[2]);
			officeAdmissionMap.put("fatherName", result[3]);
			officeAdmissionMap.put("admittedClg", result[4]);
			officeAdmissionMap.put("admittedDate", result[5]);
			officeAdmissionMap.put("totalFees", result[6]);
			officeAdmissionMap.put("paidFees", result[7]);
			officeAdmissionMap.put("paymentStatus", result[8]);
			officeAdmissionMap.put("createdBy", result[9]);

			returnList.add(officeAdmissionMap);
		}

		return returnList;
	}

}
