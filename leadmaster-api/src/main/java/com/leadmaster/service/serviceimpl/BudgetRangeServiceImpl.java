package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.BudgetRangeConverter;
import com.leadmaster.common.dao.BudgetRangeDao;
import com.leadmaster.common.domain.BudgetRange;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.BudgetRangeDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.BudgetRangeService;

@Service("BudgetRangeServiceImpl")
public class BudgetRangeServiceImpl implements BudgetRangeService {

	private static Logger LOGGER = LoggerFactory.getLogger(BudgetRangeServiceImpl.class);

	@Resource(name = "BudgetRangeDaoImpl")
	BudgetRangeDao budgetRangeDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveBudgetRange(BudgetRangeDTO budgetRangeDTO) {
		List<Role> roles = loginService.getAllUserRoles(budgetRangeDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save BudgetRange Details.");

		// Check institute type already exists in db or not
		checkBudgetRangeName(budgetRangeDTO.getBudgetRange());

		budgetRangeDao.saveBudgetRange(budgetRangeDTO);
		LOGGER.info("BudgetRange added successfully by " + budgetRangeDTO.getCreatedBy());
	}

	@Override
	public List<BudgetRange> getAllBudgetRanges(BudgetRangeDTO budgetRangeDTO) {
		return budgetRangeDao.getAllBudgetRange(budgetRangeDTO);
	}

	@Override
	public BudgetRange getBudgetRangeById(BudgetRangeDTO budgetRangeDTO) {
		return budgetRangeDao.getBudgetRangeById(budgetRangeDTO.getId());
	}

	@Override
	public void updateBudgetRange(BudgetRangeDTO budgetRangeDTO) {
		List<Role> roles = loginService.getAllUserRoles(budgetRangeDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		BudgetRange budgetRanges = budgetRangeDao.getBudgetRangeById(budgetRangeDTO.getId());
		BudgetRangeDTO dbCollegeDTO = BudgetRangeConverter.getBudgetRangeDTOByBudgetRange(budgetRanges);

		if (null != budgetRangeDTO.getBudgetRange()
				&& !budgetRangeDTO.getBudgetRange().equals(dbCollegeDTO.getBudgetRange())) {

			// Check institute type already exists in db or not
			checkBudgetRangeName(budgetRangeDTO.getBudgetRange());
			dbCollegeDTO.setBudgetRange(budgetRangeDTO.getBudgetRange());
		}

		dbCollegeDTO.setUpdatedBy(budgetRangeDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(budgetRangeDTO.getUpdatedDate());
		budgetRangeDao.saveBudgetRange(dbCollegeDTO);
		LOGGER.info(
				"BudgetRange " + budgetRangeDTO.getId() + " updated successfully by " + budgetRangeDTO.getUpdatedBy());
	}

	private void checkBudgetRangeName(String range) {
		List<BudgetRange> dbCollege = budgetRangeDao.getBudgetRangeByRange(range);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "BudgetRange already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public BudgetRange deleteBudgetRange(BudgetRangeDTO budgetRangeDTO) {
		BudgetRange dbCollege = budgetRangeDao.getBudgetRangeById(budgetRangeDTO.getId());
		BudgetRangeDTO dbCollegeDTO = BudgetRangeConverter.getBudgetRangeDTOByBudgetRange(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return budgetRangeDao.saveBudgetRange(dbCollegeDTO);

	}

}
