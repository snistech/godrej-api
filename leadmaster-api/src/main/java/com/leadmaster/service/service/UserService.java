package com.leadmaster.service.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.leadmaster.common.dto.OtpDTO;
import com.leadmaster.common.dto.UserDTO;

public interface UserService {

//	public void signup(UserDTO userDTO);

	public LinkedHashMap<String, Object> login(UserDTO userDTO);

	public void saveUser(UserDTO userDTO);

	public List<UserDTO> getAllUsers(UserDTO userDTO);

	public UserDTO getUserById(UserDTO userDTO);

	public void updateUser(UserDTO userDTO);

	public void updateUserRoles(UserDTO userDTO);

	public void changePassword(UserDTO userDTO);

	public void deleteUser(UserDTO userDTO);

	// otp manager
	public void saveOtp(OtpDTO otpDTO);

	public LinkedHashMap<String, Object> loginByOTP(OtpDTO otpDTO);

	public UserDTO getUserByPhoneNumber(UserDTO userDTO);

}
