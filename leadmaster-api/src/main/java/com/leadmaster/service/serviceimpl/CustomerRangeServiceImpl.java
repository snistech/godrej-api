package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.CustomerRangeConverter;
import com.leadmaster.common.dao.CustomerRangeDao;
import com.leadmaster.common.domain.CustomerRange;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.CustomerRangeDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.CustomerRangeService;

@Service("CustomerRangeServiceImpl")
public class CustomerRangeServiceImpl implements CustomerRangeService {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerRangeServiceImpl.class);

	@Resource(name = "CustomerRangeDaoImpl")
	CustomerRangeDao customerRangeDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveCustomerRange(CustomerRangeDTO customerRangeDTO) {
		List<Role> roles = loginService.getAllUserRoles(customerRangeDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save CustomerRange Details.");

		// Check institute type already exists in db or not
		checkCustomerRangeName(customerRangeDTO.getCustomerRange());

		customerRangeDao.saveCustomerRange(customerRangeDTO);
		LOGGER.info("CustomerRange added successfully by " + customerRangeDTO.getCreatedBy());
	}

	@Override
	public List<CustomerRange> getAllCustomerRanges(CustomerRangeDTO customerRangeDTO) {
		return customerRangeDao.getAllCustomerRange(customerRangeDTO);
	}

	@Override
	public CustomerRange getCustomerRangeById(CustomerRangeDTO customerRangeDTO) {
		return customerRangeDao.getCustomerRangeById(customerRangeDTO.getId());
	}

	@Override
	public void updateCustomerRange(CustomerRangeDTO customerRangeDTO) {
		List<Role> roles = loginService.getAllUserRoles(customerRangeDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		CustomerRange customerRanges = customerRangeDao.getCustomerRangeById(customerRangeDTO.getId());
		CustomerRangeDTO dbCollegeDTO = CustomerRangeConverter.getCustomerRangeDTOByCustomerRange(customerRanges);

		if (null != customerRangeDTO.getCustomerRange()
				&& !customerRangeDTO.getCustomerRange().equals(dbCollegeDTO.getCustomerRange())) {

			// Check institute type already exists in db or not
			checkCustomerRangeName(customerRangeDTO.getCustomerRange());
			dbCollegeDTO.setCustomerRange(customerRangeDTO.getCustomerRange());
		}

		dbCollegeDTO.setUpdatedBy(customerRangeDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(customerRangeDTO.getUpdatedDate());
		customerRangeDao.saveCustomerRange(dbCollegeDTO);
		LOGGER.info("CustomerRange " + customerRangeDTO.getId() + " updated successfully by "
				+ customerRangeDTO.getUpdatedBy());
	}

	private void checkCustomerRangeName(String range) {
		List<CustomerRange> dbCollege = customerRangeDao.getCustomerRangeByRange(range);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "CustomerRange already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public CustomerRange deleteCustomerRange(CustomerRangeDTO customerRangeDTO) {
		CustomerRange dbCollege = customerRangeDao.getCustomerRangeById(customerRangeDTO.getId());
		CustomerRangeDTO dbCollegeDTO = CustomerRangeConverter.getCustomerRangeDTOByCustomerRange(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return customerRangeDao.saveCustomerRange(dbCollegeDTO);

	}

}
