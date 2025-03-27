package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.Project;
import com.leadmaster.common.dto.ProjectDTO;

public interface ProjectDao {

	public Project saveProject(ProjectDTO projectDTO);

	public List<Project> getAllProject(ProjectDTO projectDTO);

	public Project getProjectById(Long id);

}
