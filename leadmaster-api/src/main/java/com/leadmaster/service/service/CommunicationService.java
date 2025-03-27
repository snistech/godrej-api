package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.ChatHistory;
import com.leadmaster.common.dto.ChatHistoryDTO;

public interface CommunicationService {

	// chat history manager
	public void saveChatHistory(ChatHistoryDTO chatHistoryDTO);

	public List<ChatHistory> getAllChatHistory(ChatHistoryDTO chatHistoryDTO);

	public List<Map<String, Object>> getMyChats(ChatHistoryDTO chatHistoryDTO);

	public List<Map<String, Object>> getUserChats(ChatHistoryDTO chatHistoryDTO);

	public List<Map<String, Object>> getTotalUnreadCount(ChatHistoryDTO chatHistoryDTO);

	public void updateChat(ChatHistoryDTO chatHistoryDTO);

}
