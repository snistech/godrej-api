package com.leadmaster.service.serviceimpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.CustomerTypeConverter;
import com.leadmaster.common.dao.CustomerTypeDao;
import com.leadmaster.common.domain.CustomerType;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.CustomerTypeDTO;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.CustomerTypeService;

@Service("CustomerTypeServiceImpl")
public class CustomerTypeServiceImpl implements CustomerTypeService {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerTypeServiceImpl.class);

	@Resource(name = "CustomerTypeDaoImpl")
	CustomerTypeDao customerTypeDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveCustomerType(CustomerTypeDTO customerTypeDTO) {
		List<Role> roles = loginService.getAllUserRoles(customerTypeDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save CustomerType Details.");

		// Check institute type already exists in db or not
		checkCustomerTypeName(customerTypeDTO.getType());

		customerTypeDao.saveCustomerType(customerTypeDTO);
		LOGGER.info("CustomerType added successfully by " + customerTypeDTO.getCreatedBy());
	}

	@Override
	public List<CustomerType> getAllCustomerTypes(CustomerTypeDTO customerTypeDTO) {
		return customerTypeDao.getAllCustomerType(customerTypeDTO);
	}

	@Override
	public CustomerType getCustomerTypeById(CustomerTypeDTO customerTypeDTO) {
		return customerTypeDao.getCustomerTypeById(customerTypeDTO.getId());
	}

	@Override
	public void updateCustomerType(CustomerTypeDTO customerTypeDTO) {
		List<Role> roles = loginService.getAllUserRoles(customerTypeDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Colleges Details.");

		CustomerType customerTypes = customerTypeDao.getCustomerTypeById(customerTypeDTO.getId());
		CustomerTypeDTO dbCollegeDTO = CustomerTypeConverter.getCustomerTypeDTOByCustomerType(customerTypes);

		if (null != customerTypeDTO.getType() && !customerTypeDTO.getType().equals(dbCollegeDTO.getType())) {

			// Check institute type already exists in db or not
			checkCustomerTypeName(customerTypeDTO.getType());
			dbCollegeDTO.setType(customerTypeDTO.getType());
		}

		dbCollegeDTO.setUpdatedBy(customerTypeDTO.getUpdatedBy());
		dbCollegeDTO.setUpdatedDate(customerTypeDTO.getUpdatedDate());
		customerTypeDao.saveCustomerType(dbCollegeDTO);
		LOGGER.info("CustomerType " + customerTypeDTO.getId() + " updated successfully by "
				+ customerTypeDTO.getUpdatedBy());
	}

	private void checkCustomerTypeName(String type) {
		List<CustomerType> dbCollege = customerTypeDao.getCustomerTypeByType(type);
		if (null != dbCollege && dbCollege.size() > 0) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM003");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "CustomerType already exists");
			throw new InterruptExitException(errorMap);
		}
	}

	@Override
	public CustomerType deleteCustomerType(CustomerTypeDTO customerTypeDTO) {
		CustomerType dbCollege = customerTypeDao.getCustomerTypeById(customerTypeDTO.getId());
		CustomerTypeDTO dbCollegeDTO = CustomerTypeConverter.getCustomerTypeDTOByCustomerType(dbCollege);

		dbCollegeDTO.setStatus(Constant.STATUS_INACTIVE);
		return customerTypeDao.saveCustomerType(dbCollegeDTO);

	}

}
