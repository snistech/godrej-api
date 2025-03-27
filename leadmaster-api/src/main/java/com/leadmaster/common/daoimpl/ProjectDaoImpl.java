package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.ProjectConverter;
import com.leadmaster.common.dao.ProjectDao;
import com.leadmaster.common.domain.Project;
import com.leadmaster.common.dto.ProjectDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.ProjectRepository;

@Transactional
@Service("ProjectDaoImpl")
public class ProjectDaoImpl implements ProjectDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project saveProject(ProjectDTO projectDTO) {
		Project projects = ProjectConverter.getProjectByProjectDTO(projectDTO);
		return projectRepository.save(projects);
	}

	@Override
	public List<Project> getAllProject(ProjectDTO projectDTO) {
		List<Project> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Project a where 1=1");

		if (null != projectDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != projectDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != projectDTO.getCategory())
			sqlQuery.append(" AND a.category = :category");
		if (null != projectDTO.getType())
			sqlQuery.append(" AND a.type = :type");
		if (null != projectDTO.getProjectName())
			sqlQuery.append(" AND a.projectName = :projectName");

		sqlQuery.append(" ORDER BY a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != projectDTO.getId())
			query.setParameter("id", projectDTO.getId());
		if (null != projectDTO.getStatus())
			query.setParameter("status", projectDTO.getStatus());
		if (null != projectDTO.getCategory())
			query.setParameter("category", projectDTO.getCategory());
		if (null != projectDTO.getType())
			query.setParameter("type", projectDTO.getType());
		if (null != projectDTO.getProjectName())
			query.setParameter("projectName", projectDTO.getProjectName());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public Project getProjectById(Long id) {
		Optional<Project> projects = projectRepository.findById(id);
		if (!projects.isPresent())
			throw new ResourceNotFoundException("The project is not found in the system. id:" + id);
		return projects.get();
	}

}
