package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.RoleDTO;

public interface RoleDao {

	public Role saveRole(RoleDTO roleDTO);

	public void deleteRoleByUserId(Long userId);

	public List<Role> getAllRoles(RoleDTO roleDTO);

}
