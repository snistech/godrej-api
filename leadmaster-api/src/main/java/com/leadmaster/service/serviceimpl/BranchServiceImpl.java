package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.BranchConverter;
import com.leadmaster.common.dao.BranchDao;
import com.leadmaster.common.domain.Branch;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.BranchDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.BranchService;

@Service("BranchServiceImpl")
public class BranchServiceImpl implements BranchService {

	private static Logger LOGGER = LoggerFactory.getLogger(BranchServiceImpl.class);

	@Resource(name = "BranchDaoImpl")
	BranchDao branchDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveBranch(BranchDTO branchDTO) {
		List<Role> roles = loginService.getAllUserRoles(branchDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save Branch Details.");

		// Check institute type already exists in db or not
		checkBranch(branchDTO.getBranch());

		branchDao.saveBranch(branchDTO);
		LOGGER.info("Branch added successfully by " + branchDTO.getCreatedBy());
	}

	@Override
	public List<Branch> getAllBranchs(BranchDTO branchDTO) {
		return branchDao.getAllBranch(branchDTO);
	}

	@Override
	public Branch getBranchById(BranchDTO branchDTO) {
		return branchDao.getBranchById(branchDTO.getId());
	}

	@Override
	public void updateBranch(BranchDTO branchDTO) {
		List<Role> roles = loginService.getAllUserRoles(branchDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Branchs Details.");

		Branch branchs = branchDao.getBranchById(branchDTO.getId());
		BranchDTO dbBranchDTO = BranchConverter.getBranchDTOByBranch(branchs);

		if (null != branchDTO.getBranch() && !branchDTO.getBranch().equals(dbBranchDTO.getBranch())) {

			// Check institute type already exists in db or not
			checkBranch(branchDTO.getBranch());
			dbBranchDTO.setBranch(branchDTO.getBranch());
		}

		dbBranchDTO.setUpdatedBy(branchDTO.getUpdatedBy());
		dbBranchDTO.setUpdatedDate(branchDTO.getUpdatedDate());
		branchDao.saveBranch(dbBranchDTO);
		LOGGER.info("Branch " + branchDTO.getId() + " updated successfully by " + branchDTO.getUpdatedBy());
	}

	private void checkBranch(String branchName) {
		List<Branch> dbBranch = branchDao.getBranchByName(branchName);
		if (null != dbBranch && dbBranch.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Branch already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public Branch deleteBranch(BranchDTO branchDTO) {
		Branch dbBranch = branchDao.getBranchById(branchDTO.getId());
		BranchDTO dbBranchDTO = BranchConverter.getBranchDTOByBranch(dbBranch);

		dbBranchDTO.setStatus(Constant.STATUS_INACTIVE);
		return branchDao.saveBranch(dbBranchDTO);

	}

}
