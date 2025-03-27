package com.leadmaster.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadmaster.common.domain.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

}
