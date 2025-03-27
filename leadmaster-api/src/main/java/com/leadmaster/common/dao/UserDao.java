package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.User;
import com.leadmaster.common.dto.UserDTO;

public interface UserDao {

	public User saveUser(UserDTO usersDTO);

	public List<User> getAllUser(UserDTO usersDTO);

	public User getUserById(Long id);

	public User getUserByEmail(String email);

	public List<String> getUserIdsByLocationAndBranch(String location, String branch);

	public String getUserBranchByCreatedBy(Long createdBy);

	public List<String> getUserIdsByAssignedAsset(String college);

	public List<String> getUserIdsByBranchAndAssignedAsset(String branch, String college);

	public User getUserByPhoneNumber(String phoneNumber);
}
