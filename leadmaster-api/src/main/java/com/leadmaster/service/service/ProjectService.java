package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.Project;
import com.leadmaster.common.dto.ProjectDTO;

public interface ProjectService {

	public void saveProject(ProjectDTO projectDTO);

	public List<Project> getAllProjects(ProjectDTO projectDTO);

	public Project getProjectById(ProjectDTO projectDTO);

	public void updateProject(ProjectDTO projectDTO);

	public Project deleteProject(ProjectDTO projectDTO);

}
