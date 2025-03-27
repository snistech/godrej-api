package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.Branch;
import com.leadmaster.common.dto.BranchDTO;

public interface BranchService {

	public void saveBranch(BranchDTO branchDTO);

	public List<Branch> getAllBranchs(BranchDTO branchDTO);

	public Branch getBranchById(BranchDTO branchDTO);

	public void updateBranch(BranchDTO branchDTO);

	public Branch deleteBranch(BranchDTO branchDTO);

}
