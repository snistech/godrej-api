package com.leadmaster.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadmaster.common.domain.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}
