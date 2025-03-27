package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.Branch;
import com.leadmaster.common.dto.BranchDTO;

public interface BranchDao {

	public Branch saveBranch(BranchDTO branchDTO);

	public List<Branch> getAllBranch(BranchDTO branchDTO);

	public Branch getBranchById(Long id);

	public List<Branch> getBranchByName(String branchName);
}
