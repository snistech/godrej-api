package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.OfficeAdmission;
import com.leadmaster.common.dto.OfficeAdmissionDTO;

@Component
public class OfficeAdmissionConverter {

	public static OfficeAdmission getOfficeAdmissionByOfficeAdmissionDTO(OfficeAdmissionDTO officeAdmissionDTO) {
		OfficeAdmission officeAdmission = new OfficeAdmission();
		officeAdmission.setId(officeAdmissionDTO.getId());
		officeAdmission.setFullName(officeAdmissionDTO.getFullName());
		officeAdmission.setPhoneNumber(officeAdmissionDTO.getPhoneNumber());
		officeAdmission.setAlternativeNumber(officeAdmissionDTO.getAlternativeNumber());
		officeAdmission.setEmail(officeAdmissionDTO.getEmail());
		officeAdmission.setFatherName(officeAdmissionDTO.getFatherName());
		officeAdmission.setMotherName(officeAdmissionDTO.getMotherName());
		officeAdmission.setAdharProof(officeAdmissionDTO.getAdharProof());
		officeAdmission.setAddress(officeAdmissionDTO.getAddress());
		officeAdmission.setCourse(officeAdmissionDTO.getCourse());
		officeAdmission.setAdmittedDate(officeAdmissionDTO.getAdmittedDate());
		officeAdmission.setAdmittedClg(officeAdmissionDTO.getAdmittedClg());
		officeAdmission.setTotalFees(officeAdmissionDTO.getTotalFees());
		officeAdmission.setPaidFees(officeAdmissionDTO.getPaidFees());
		officeAdmission.setPaymentStatus(officeAdmissionDTO.getPaymentStatus());
		officeAdmission.setAdmissionStatus(officeAdmissionDTO.getAdmissionStatus());
		officeAdmission.setApprovalStatus(officeAdmissionDTO.getApprovalStatus());
		officeAdmission.setRemarks(officeAdmissionDTO.getRemarks());
		officeAdmission.setStatus(officeAdmissionDTO.getStatus());
		officeAdmission.setCreatedDate(officeAdmissionDTO.getCreatedDate());
		officeAdmission.setCreatedBy(officeAdmissionDTO.getCreatedBy());
		officeAdmission.setUpdatedDate(officeAdmissionDTO.getUpdatedDate());
		officeAdmission.setUpdatedBy(officeAdmissionDTO.getUpdatedBy());

		return officeAdmission;
	}

	public static OfficeAdmissionDTO getOfficeAdmissionDTOByOfficeAdmission(OfficeAdmission officeAdmission) {
		OfficeAdmissionDTO dto = new OfficeAdmissionDTO();

		dto.setId(officeAdmission.getId());
		dto.setFullName(officeAdmission.getFullName());
		dto.setPhoneNumber(officeAdmission.getPhoneNumber());
		dto.setAlternativeNumber(officeAdmission.getAlternativeNumber());
		dto.setEmail(officeAdmission.getEmail());
		dto.setFatherName(officeAdmission.getFatherName());
		dto.setMotherName(officeAdmission.getMotherName());
		dto.setAdharProof(officeAdmission.getAdharProof());
		dto.setAddress(officeAdmission.getAddress());
		dto.setCourse(officeAdmission.getCourse());
		dto.setAdmittedDate(officeAdmission.getAdmittedDate());
		dto.setAdmittedClg(officeAdmission.getAdmittedClg());
		dto.setTotalFees(officeAdmission.getTotalFees());
		dto.setPaidFees(officeAdmission.getPaidFees());
		dto.setPaymentStatus(officeAdmission.getPaymentStatus());
		dto.setAdmissionStatus(officeAdmission.getAdmissionStatus());
		dto.setApprovalStatus(officeAdmission.getApprovalStatus());
		dto.setRemarks(officeAdmission.getRemarks());
		dto.setStatus(officeAdmission.getStatus());
		dto.setCreatedDate(officeAdmission.getCreatedDate());
		dto.setCreatedBy(officeAdmission.getCreatedBy());
		dto.setUpdatedDate(officeAdmission.getUpdatedDate());
		dto.setUpdatedBy(officeAdmission.getUpdatedBy());

		return dto;
	}

}
