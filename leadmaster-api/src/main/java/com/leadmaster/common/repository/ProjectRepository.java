package com.leadmaster.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadmaster.common.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
