package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.ChatHistoryConverter;
import com.leadmaster.common.dao.ChatHistoryDao;
import com.leadmaster.common.domain.ChatHistory;
import com.leadmaster.common.dto.ChatHistoryDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.ChatHistoryRepository;

@Transactional
@Service("ChatHistoryDaoImpl")
public class ChatHistoryDaoImpl implements ChatHistoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ChatHistoryRepository chatHistoryRepository;

	@Override
	public ChatHistory saveChatHistory(ChatHistoryDTO chatHistoryDTO) {
		ChatHistory chatHistorys = ChatHistoryConverter.getChatHistoryByChatHistoryDTO(chatHistoryDTO);
		return chatHistoryRepository.save(chatHistorys);
	}

	@Override
	public List<ChatHistory> getAllChatHistory(ChatHistoryDTO chatHistoryDTO) {
		List<ChatHistory> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from ChatHistory a where 1=1");

		if (null != chatHistoryDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != chatHistoryDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != chatHistoryDTO.getCreatedBy())
			sqlQuery.append(" AND a.createdBy = :createdBy");

		sqlQuery.append(" ORDER BY a.updatedDate DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != chatHistoryDTO.getId())
			query.setParameter("id", chatHistoryDTO.getId());
		if (null != chatHistoryDTO.getStatus())
			query.setParameter("status", chatHistoryDTO.getStatus());
		if (null != chatHistoryDTO.getCreatedBy())
			query.setParameter("createdBy", chatHistoryDTO.getCreatedBy());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public ChatHistory getChatHistoryById(Long id) {
		Optional<ChatHistory> chatHistorys = chatHistoryRepository.findById(id);
		if (!chatHistorys.isPresent())
			throw new ResourceNotFoundException("The chatHistory is not found in the system. id:" + id);
		return chatHistorys.get();
	}

	@Override
	public List<Map<String, Object>> getMyChats(ChatHistoryDTO chatHistoryDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ").append("u.id AS userId, ").append("u.name AS userName, ")
				.append("ch.text AS lastMessage, ").append("ch.created_date AS lastMessageTime, ")
				.append("(SELECT COUNT(*) FROM chat_history ch2 ").append(" WHERE ch2.read_status = 'Unread' ")
				.append(" AND ch2.status = 'Active' ")
				.append(" AND ((ch2.sender_id = :currentUserId AND ch2.receiver_id = latestChats.contactId) ")
				.append(" OR (ch2.sender_id = latestChats.contactId AND ch2.receiver_id = :currentUserId))) AS unreadCount ")
				.append("FROM ( ").append("    SELECT ")
				.append("    CASE WHEN sender_id = :currentUserId THEN receiver_id ELSE sender_id END AS contactId, ")
				.append("    MAX(created_date) AS lastTimestamp ").append("    FROM chat_history ")
				.append("    WHERE sender_id = :currentUserId OR receiver_id = :currentUserId ")
				.append("    GROUP BY CASE WHEN sender_id = :currentUserId THEN receiver_id ELSE sender_id END ")
				.append(") latestChats ")
				.append("INNER JOIN chat_history ch ON ch.created_date = latestChats.lastTimestamp ")
				.append("INNER JOIN users u ON u.id = latestChats.contactId ").append("ORDER BY ch.created_date DESC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		query.setParameter("currentUserId", chatHistoryDTO.getUpdatedBy());

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> chatMap = new LinkedHashMap<>();
			chatMap.put("userId", result[0]);
			chatMap.put("userName", result[1]);
			chatMap.put("lastMessage", result[2]);
			chatMap.put("lastMessageTime", result[3]);
			chatMap.put("unreadCount", result[4]);

			returnList.add(chatMap);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getUserChats(ChatHistoryDTO chatHistoryDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ").append("DATE(ch.created_date) AS chatDate, ") // Group by date
				.append("ch.id AS chatId, ").append("ch.text AS message, ")
				.append("CASE WHEN ch.sender_id = :currentUserId THEN 'Sent' ELSE 'Received' END AS messageType, ")
				.append("CASE WHEN ch.read_status = 'Unread' THEN 'Unread' ELSE 'Read' END AS readStatus, ")
				.append("ch.created_date AS messageTime ").append("FROM chat_history ch ")
				.append("WHERE (ch.sender_id = :currentUserId AND ch.receiver_id = :contactUserId) ")
				.append("   OR (ch.sender_id = :contactUserId AND ch.receiver_id = :currentUserId) ")
				.append("  AND ch.status = 'Active' ").append("ORDER BY ch.created_date ASC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		query.setParameter("currentUserId", chatHistoryDTO.getUpdatedBy());
		query.setParameter("contactUserId", chatHistoryDTO.getContactUserId());

		List<Object[]> resultList = query.getResultList();

		// Group messages by date
		Map<String, List<Map<String, Object>>> groupedChats = new LinkedHashMap<>();

		for (Object[] result : resultList) {
			String chatDate = result[0].toString();
			Map<String, Object> messageMap = new LinkedHashMap<>();
			messageMap.put("id", result[1]);
			messageMap.put("message", result[2]);
			messageMap.put("messageType", result[3]);
			messageMap.put("readStatus", result[4]);
			messageMap.put("messageTime", result[5]);

			groupedChats.computeIfAbsent(chatDate, k -> new ArrayList<>()).add(messageMap);
		}

		for (Map.Entry<String, List<Map<String, Object>>> entry : groupedChats.entrySet()) {
			Map<String, Object> dateGroup = new LinkedHashMap<>();
			dateGroup.put("date", entry.getKey());
			dateGroup.put("chats", entry.getValue());
			returnList.add(dateGroup);
		}

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getTotalUnreadCount(ChatHistoryDTO chatHistoryDTO) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ").append("COUNT(*) AS totalUnreadCount ").append("FROM chat_history ch ")
				.append("WHERE ch.receiver_id = :currentUserId ").append("  AND ch.read_status = 'Unread' ")
				.append("  AND ch.status = 'Active'");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		query.setParameter("currentUserId", chatHistoryDTO.getUpdatedBy());

		Object result = query.getSingleResult();

		List<Map<String, Object>> returnList = new ArrayList<>();
		Map<String, Object> countMap = new LinkedHashMap<>();
		countMap.put("totalUnreadCount", result != null ? ((Number) result).longValue() : 0);

		returnList.add(countMap);
		return returnList;
	}

}
