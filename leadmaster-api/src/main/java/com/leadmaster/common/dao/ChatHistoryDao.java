package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.ChatHistory;
import com.leadmaster.common.dto.ChatHistoryDTO;

public interface ChatHistoryDao {

	public ChatHistory saveChatHistory(ChatHistoryDTO chatHistoryDTO);

	public List<ChatHistory> getAllChatHistory(ChatHistoryDTO chatHistoryDTO);

	public ChatHistory getChatHistoryById(Long id);

	public List<Map<String, Object>> getMyChats(ChatHistoryDTO chatHistoryDTO);

	public List<Map<String, Object>> getUserChats(ChatHistoryDTO chatHistoryDTO);

	public List<Map<String, Object>> getTotalUnreadCount(ChatHistoryDTO chatHistoryDTO);

}
