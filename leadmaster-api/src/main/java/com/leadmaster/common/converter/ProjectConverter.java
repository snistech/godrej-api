package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Project;
import com.leadmaster.common.dto.ProjectDTO;

@Component
public class ProjectConverter {

	public static Project getProjectByProjectDTO(ProjectDTO projectDTO) {
		Project project = new Project();

		project.setId(projectDTO.getId());
		project.setCategory(projectDTO.getCategory());
		project.setType(projectDTO.getType());
		project.setProjectName(projectDTO.getProjectName());
		project.setStatus(projectDTO.getStatus());
		project.setCreatedDate(projectDTO.getCreatedDate());
		project.setCreatedBy(projectDTO.getCreatedBy());
		project.setUpdatedDate(projectDTO.getUpdatedDate());
		project.setUpdatedBy(projectDTO.getUpdatedBy());

		return project;
	}

	public static ProjectDTO getProjectDTOByProject(Project project) {
		ProjectDTO dto = new ProjectDTO();

		dto.setId(project.getId());
		dto.setCategory(project.getCategory());
		dto.setType(project.getType());
		dto.setProjectName(project.getProjectName());
		dto.setStatus(project.getStatus());
		dto.setCreatedDate(project.getCreatedDate());
		dto.setCreatedBy(project.getCreatedBy());
		dto.setUpdatedDate(project.getUpdatedDate());
		dto.setUpdatedBy(project.getUpdatedBy());

		return dto;
	}

}
