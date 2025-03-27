package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Branch;
import com.leadmaster.common.dto.BranchDTO;

@Component
public class BranchConverter {

	public static Branch getBranchByBranchDTO(BranchDTO branchDTO) {
		Branch branch = new Branch();
		branch.setId(branchDTO.getId());
		branch.setBranch(branchDTO.getBranch());
		branch.setStatus(branchDTO.getStatus());
		branch.setCreatedDate(branchDTO.getCreatedDate());
		branch.setCreatedBy(branchDTO.getCreatedBy());
		branch.setUpdatedDate(branchDTO.getUpdatedDate());
		branch.setUpdatedBy(branchDTO.getUpdatedBy());

		return branch;
	}

	public static BranchDTO getBranchDTOByBranch(Branch branch) {
		BranchDTO dto = new BranchDTO();

		dto.setId(branch.getId());
		dto.setBranch(branch.getBranch());
		dto.setStatus(branch.getStatus());
		dto.setCreatedDate(branch.getCreatedDate());
		dto.setCreatedBy(branch.getCreatedBy());
		dto.setUpdatedDate(branch.getUpdatedDate());
		dto.setUpdatedBy(branch.getUpdatedBy());

		return dto;
	}

}
