package com.leadmaster.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.ChatHistoryConverter;
import com.leadmaster.common.dao.ChatHistoryDao;
import com.leadmaster.common.domain.ChatHistory;
import com.leadmaster.common.dto.ChatHistoryDTO;
import com.leadmaster.common.service.LoginService;
import com.leadmaster.service.service.CommunicationService;

@Service("CommunicationServiceImpl")
public class CommunicationServiceImpl implements CommunicationService {

	private static Logger LOGGER = LoggerFactory.getLogger(CommunicationServiceImpl.class);

	@Resource(name = "ChatHistoryDaoImpl")
	ChatHistoryDao chatHistoryDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	// chat history manager
	@Override
	public void saveChatHistory(ChatHistoryDTO chatHistoryDTO) {

		chatHistoryDao.saveChatHistory(chatHistoryDTO);
		LOGGER.info("chatHistory added successfully by " + chatHistoryDTO.getCreatedBy());
	}

	@Override
	public List<ChatHistory> getAllChatHistory(ChatHistoryDTO chatHistoryDTO) {
		return chatHistoryDao.getAllChatHistory(chatHistoryDTO);
	}

	@Override
	public List<Map<String, Object>> getMyChats(ChatHistoryDTO chatHistoryDTO) {
		return chatHistoryDao.getMyChats(chatHistoryDTO);
	}

	@Override
	public List<Map<String, Object>> getUserChats(ChatHistoryDTO chatHistoryDTO) {
		return chatHistoryDao.getUserChats(chatHistoryDTO);
	}

	@Override
	public List<Map<String, Object>> getTotalUnreadCount(ChatHistoryDTO chatHistoryDTO) {
		return chatHistoryDao.getTotalUnreadCount(chatHistoryDTO);
	}

	@Override
	public void updateChat(ChatHistoryDTO chatHistoryDTO) {
		ChatHistory chatHistorys = chatHistoryDao.getChatHistoryById(chatHistoryDTO.getId());
		ChatHistoryDTO dbChatHistoryDTO = ChatHistoryConverter.getChatHistoryDTOByChatHistory(chatHistorys);

		if (null != chatHistoryDTO.getReadStatus())
			dbChatHistoryDTO.setReadStatus(chatHistoryDTO.getReadStatus());

		if (null != chatHistoryDTO.getStatus())
			dbChatHistoryDTO.setStatus(chatHistoryDTO.getStatus());

		dbChatHistoryDTO.setUpdatedBy(chatHistoryDTO.getUpdatedBy());
		dbChatHistoryDTO.setUpdatedDate(chatHistoryDTO.getUpdatedDate());
		chatHistoryDao.saveChatHistory(dbChatHistoryDTO);
		LOGGER.info(
				"ChatHistory " + chatHistoryDTO.getId() + " updated successfully by " + chatHistoryDTO.getUpdatedBy());
	}

}
