package com.leadmaster.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.converter.ProjectConverter;
import com.leadmaster.common.dao.ProjectDao;
import com.leadmaster.common.domain.Project;
import com.leadmaster.common.domain.Role;
import com.leadmaster.common.dto.ProjectDTO;
import com.leadmaster.common.exception.UnAuthorizedException;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.common.validator.RoleEnum;
import com.leadmaster.service.service.ProjectService;

@Service("ProjectServiceImpl")
public class ProjectServiceImpl implements ProjectService {

	private static Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Resource(name = "ProjectDaoImpl")
	ProjectDao projectDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Override
	public void saveProject(ProjectDTO projectDTO) {
		List<Role> roles = loginService.getAllUserRoles(projectDTO.getCreatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to save Project Details.");

		projectDao.saveProject(projectDTO);
		LOGGER.info("Project added successfully by " + projectDTO.getCreatedBy());
	}

	@Override
	public List<Project> getAllProjects(ProjectDTO projectDTO) {
		return projectDao.getAllProject(projectDTO);
	}

	@Override
	public Project getProjectById(ProjectDTO projectDTO) {
		return projectDao.getProjectById(projectDTO.getId());
	}

	@Override
	public void updateProject(ProjectDTO projectDTO) {
		List<Role> roles = loginService.getAllUserRoles(projectDTO.getUpdatedBy());
		boolean adminAccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update Projects Details.");

		Project projects = projectDao.getProjectById(projectDTO.getId());
		ProjectDTO dbProjectDTO = ProjectConverter.getProjectDTOByProject(projects);

		if (null != projectDTO.getCategory())
			dbProjectDTO.setCategory(projectDTO.getCategory());

		if (null != projectDTO.getType())
			dbProjectDTO.setType(projectDTO.getType());

		if (null != projectDTO.getProjectName())
			dbProjectDTO.setProjectName(projectDTO.getProjectName());

		dbProjectDTO.setUpdatedBy(projectDTO.getUpdatedBy());
		dbProjectDTO.setUpdatedDate(projectDTO.getUpdatedDate());
		projectDao.saveProject(dbProjectDTO);
		LOGGER.info("Project " + projectDTO.getId() + " updated successfully by " + projectDTO.getUpdatedBy());
	}

	@Override
	public Project deleteProject(ProjectDTO projectDTO) {
		Project dbProject = projectDao.getProjectById(projectDTO.getId());
		ProjectDTO dbProjectDTO = ProjectConverter.getProjectDTOByProject(dbProject);

		dbProjectDTO.setStatus(Constant.STATUS_INACTIVE);
		return projectDao.saveProject(dbProjectDTO);

	}

}
