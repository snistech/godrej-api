package com.leadmaster.service.serviceimpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leadmaster.common.cache.UserCache;
import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.UserConverter;
import com.leadmaster.common.dao.OtpDao;
import com.leadmaster.common.dao.RoleDao;
import com.leadmaster.common.dao.UserDao;
import com.leadmaster.common.domain.Otp;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.domain.User;
import com.leadmaster.common.dto.OtpDTO;
import com.leadmaster.common.dto.RoleDTO;
import com.leadmaster.common.dto.UserDTO;
import com.leadmaster.common.exception.FieldException;
import com.leadmaster.common.exception.InterruptExitException;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.config.JwtTokenUtil;
import com.leadmaster.service.service.UserService;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	LinkedHashMap<String, Object> returnMap = null;

	@Resource(name = "UserDaoImpl")
	private UserDao userDao;

	@Resource(name = "OtpDaoImpl")
	private OtpDao otpDao;

	@Resource(name = "RoleDaoImpl")
	private RoleDao roleDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserCache userCache;

	@Autowired
	public UserServiceImpl(PasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

//	@Override
//	public void signup(UserDTO userDTO) {
//
//		// Check user email already exists in db or not
//		User dbUser = userDao.getUserByEmail(userDTO.getEmail());
//		if (null != dbUser) {
//			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
//			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM001");
//			errorMap.put(Constant.RESPONSE_MSG_KEY, "Email already exists");
//			throw new InterruptExitException(errorMap);
//		}
//
//		// save user details
//		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
//		User user = userDao.saveUser(userDTO);
//		LOGGER.info("User added successfully with email" + user.getEmail());
//
//		// save role as student
//		userDTO.setCreatedBy(user.getId());
//		saveRole(userDTO, user, RoleEnum.STUDENT.getRole());
//
//	}

	@Override
	public LinkedHashMap<String, Object> login(UserDTO userDTO) {
		returnMap = new LinkedHashMap<String, Object>();

		// Check user exits in db or not
		User dbUser = userDao.getUserByEmail(userDTO.getEmail());
		if (null == dbUser)
			throw new UnAuthorizedException("No user found with this email::" + userDTO.getEmail());

		// Check given password matches with db password
		if (!bCryptPasswordEncoder.matches(userDTO.getPassword(), dbUser.getPassword()))
			throw new FieldException("Password missMatch for email::" + userDTO.getEmail());

		returnMap.put(Constant.RESPONSE_CODE_KEY, Constant.SUCCESSFULL_CODE);
		returnMap.put(Constant.RESPONSE_MSG_KEY, Constant.SUCCESSFULL_MSG);
		final String token = jwtTokenUtil.generateCustomToken(dbUser);
		returnMap.put("userId", dbUser.getId());
		returnMap.put("token", token);

		return returnMap;

	}

	@Override
	public void saveUser(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getCreatedBy());
		boolean adminFlag = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!(adminFlag))
			throw new UnAuthorizedException("LogedIn User does't have permission to save User Details.");

		// Check given email already exists in db or not
		User dbUser = userDao.getUserByEmail(userDTO.getEmail());
		if (null != dbUser) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GE001-1");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Email already exists");
			throw new InterruptExitException(errorMap);
		}

		// Step 1:: Generate a random password
		String password = "LeadMaster@2024";

		// Step 2:: save user with password encrypted
		userDTO.setPassword(bCryptPasswordEncoder.encode(password));
		User user = userDao.saveUser(userDTO);
		LOGGER.info("user added successfully with email::" + user.getEmail());

//		String subject = "User Access - SNInfo Systems";
//		StringBuilder body = new StringBuilder();
//		body.append("Dear ").append(userDTO.getName()).append(",<br><br>");
//		body.append("Your account has been created successfully.<br>");
//		body.append("Here your access details:<br>");
//		body.append("<strong>Email:</strong> ").append(userDTO.getEmail()).append("<br>");
//		body.append("<strong>Password:</strong> ").append(password).append("<br><br>");
//		body.append("<strong>Please login here: https://www.getmycolleges.com</strong> ").append("<br><br>");
////		body.append("Please change your password after logging in for the first time.<br><br>");
//		body.append("Best regards,<br>");
//		body.append("SNInfo Systems Team");
//
//		emailCommonService.sendMailWithHtmlBody(userDTO.getEmail(), subject, body.toString());

		// Step 3: save user roles
		if (null != userDTO.getRoles() && userDTO.getRoles().size() > 0) {
			for (String role : userDTO.getRoles())
				saveRole(userDTO, user, role);
		}

	}

	@Override
	public List<UserDTO> getAllUsers(UserDTO userDTO) {
		List<UserDTO> returnList = new ArrayList<>();
		List<User> userList = userDao.getAllUser(userDTO);

		for (User user : userList) {
			UserDTO dbUserDTO = UserConverter.getUserDTOByUser(user);
			dbUserDTO.setPassword(null);

			// Step 1: get role details
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setUserId(user.getId());
			roleDTO.setStatus(Constant.STATUS_ACTIVE);
			List<Role> roles = roleDao.getAllRoles(roleDTO);

			// Step 2: Check if the user has the specified role
			List<String> requiredRoles = userDTO.getRoles();
			if (requiredRoles != null && !requiredRoles.isEmpty()) {
				boolean hasRole = roles.stream().anyMatch(role -> requiredRoles.contains(role.getRole()));
				if (!hasRole) {
					// Skip this user if it doesn't have the required role
					continue;
				}
			}

			dbUserDTO.setRoles(roles.stream().map(Role::getRole).collect(Collectors.toList()));
			returnList.add(dbUserDTO);
		}

		return returnList;
	}

	@Override
	public UserDTO getUserById(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess) {
			if (!userDTO.getId().equals(userDTO.getUpdatedBy()))
				throw new UnAuthorizedException("LogedIn User does't have permission to get User Details.");
		}

		// Step 1: get user details.
		User user = userDao.getUserById(userDTO.getId());
		UserDTO returnDTO = UserConverter.getUserDTOByUser(user);
		returnDTO.setPassword(null);

		// Step 2: get role details
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setUserId(user.getId());
		roleDTO.setStatus(Constant.STATUS_ACTIVE);
		List<Role> list = roleDao.getAllRoles(roleDTO);
		returnDTO.setRoles(list.stream().map(Role::getRole).collect(Collectors.toList()));

		return returnDTO;
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess) {
			if (!userDTO.getId().equals(userDTO.getUpdatedBy()))
				throw new UnAuthorizedException("LogedIn User does't have permission to update user Details.");
		}

		// Check user exists or not
		User users = userDao.getUserById(userDTO.getId());
		UserDTO dbUserDTO = UserConverter.getUserDTOByUser(users);

//		if (null != userDTO.getEmail())
//			dbUserDTO.setEmail(userDTO.getEmail());

		if (null != userDTO.getName())
			dbUserDTO.setName(userDTO.getName());

		if (null != userDTO.getPhoneNumber())
			dbUserDTO.setPhoneNumber(userDTO.getPhoneNumber());

		if (null != userDTO.getLeadLocation())
			dbUserDTO.setLeadLocation(userDTO.getLeadLocation());

		if (null != userDTO.getBranch())
			dbUserDTO.setBranch(userDTO.getBranch());

		if (null != userDTO.getAssignedAsset())
			dbUserDTO.setAssignedAsset(userDTO.getAssignedAsset());

		if (null != userDTO.getActiveStatus())
			dbUserDTO.setActiveStatus(userDTO.getActiveStatus());

		dbUserDTO.setUpdatedBy(userDTO.getUpdatedBy());
		dbUserDTO.setUpdatedDate(userDTO.getUpdatedDate());
		userDao.saveUser(dbUserDTO);
		LOGGER.info("User details for user id " + dbUserDTO.getId() + " are updated successfully.");
	}

	@Override
	public void updateUserRoles(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update UserRoles Details.");

		User user = userDao.getUserById(userDTO.getId());

		// Step 1:: delete exiting userRoles
		roleDao.deleteRoleByUserId(userDTO.getId());

		// Step 2:: insert new user roles
		for (String role : userDTO.getRoles())
			saveRole(userDTO, user, role);

		LOGGER.info("User roles for user id " + user.getId() + " are updated successfully.");

	}

	@Override
	public void changePassword(UserDTO userDTO) {

		// Step 1:Get user details
		User dbUser = userDao.getUserById(userDTO.getUpdatedBy());
		UserDTO dbUserDTO = UserConverter.getUserDTOByUser(dbUser);

		dbUserDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		dbUserDTO.setUpdatedBy(userDTO.getUpdatedBy());
		dbUserDTO.setUpdatedDate(userDTO.getUpdatedDate());
		userDao.saveUser(dbUserDTO);
		LOGGER.info("Password changed successfully for email " + dbUserDTO.getEmail());

	}

	private void saveRole(UserDTO userDTO, User user, String role) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setUserId(user.getId());
		roleDTO.setRole(role);
		roleDTO.setStatus(Constant.STATUS_ACTIVE);
		roleDTO.setCreatedBy(userDTO.getCreatedBy());
		roleDTO.setCreatedDate(userDTO.getCreatedDate());
		roleDao.saveRole(roleDTO);

	}

	@Override
	public void deleteUser(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess) {
			if (!userDTO.getId().equals(userDTO.getUpdatedBy()))
				throw new UnAuthorizedException("LogedIn User does't have permission to delete user.");
		}

		// Check user exists or not
		User users = userDao.getUserById(userDTO.getId());
		UserDTO dbUserDTO = UserConverter.getUserDTOByUser(users);

		dbUserDTO.setStatus(Constant.STATUS_INACTIVE);
		dbUserDTO.setUpdatedBy(userDTO.getUpdatedBy());
		dbUserDTO.setUpdatedDate(userDTO.getUpdatedDate());
		userDao.saveUser(dbUserDTO);
		LOGGER.info("User for user id " + dbUserDTO.getId() + " are deleted successfully.");
	}

	// login by otp
	@Override
	public LinkedHashMap<String, Object> loginByOTP(OtpDTO otpDTO) {
		LinkedHashMap<String, Object> returnMap = new LinkedHashMap<String, Object>();

		// Check if user exists in db or not
		Otp dbOtp = otpDao.getOtpByPhoneNumber(otpDTO.getPhoneNumber(), otpDTO.getOtp());
		if (dbOtp == null) {
			throw new UnAuthorizedException("No OTP found with this phoneNumber::" + otpDTO.getPhoneNumber());
		}

		// Parse the created_date string to LocalDateTime (assuming it is in IST)
		String createdDateString = dbOtp.getCreatedDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime otpCreationTime = LocalDateTime.parse(createdDateString, formatter);

		// Get the current time in IST
		ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
		LocalDateTime currentTime = LocalDateTime.now(istZoneId);

		// Check if OTP is valid (created within the last 5 minutes)
		Duration duration = Duration.between(otpCreationTime, currentTime);
		long minutesDifference = duration.toMinutes();

		if (minutesDifference > 5) {
			throw new UnAuthorizedException("OTP has expired.");
		}

		// Check if email exists in the user table
		User dbUser = userDao.getUserByPhoneNumber(otpDTO.getPhoneNumber());

		if (dbUser == null) {
			throw new UnAuthorizedException("No User found with this phoneNumber::" + otpDTO.getPhoneNumber());
		}

		String token = null;
		Long userId = dbUser.getId();

		if (dbUser != null && dbUser.getEmail() != null && dbUser.getId() != null) {
			// Generate the token if email exists and is not null
			token = jwtTokenUtil.generateCustomToken(dbUser);
		}

		// OTP is valid, build response
		returnMap.put(Constant.RESPONSE_CODE_KEY, Constant.SUCCESSFULL_CODE);
		returnMap.put(Constant.RESPONSE_MSG_KEY, Constant.SUCCESSFULL_MSG);
		returnMap.put("userId", userId);
		returnMap.put("token", token);

		return returnMap;
	}

	@Override
	public void saveOtp(OtpDTO otpDTO) {
		Otp otp = otpDao.saveOtp(otpDTO);
		LOGGER.info("otp saved successfully");
	}

	@Override
	public UserDTO getUserByPhoneNumber(UserDTO userDTO) {

		User user = userDao.getUserByPhoneNumber(userDTO.getPhoneNumber());
		UserDTO returnDTO = UserConverter.getUserDTOByUser(user);
		returnDTO.setPassword(null);

		return returnDTO;
	}

}
