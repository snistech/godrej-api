package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.ChatHistory;
import com.leadmaster.common.dto.ChatHistoryDTO;

@Component
public class ChatHistoryConverter {
	public static ChatHistory getChatHistoryByChatHistoryDTO(ChatHistoryDTO chatHistoryDTO) {

		ChatHistory chatHistory = new ChatHistory();

		chatHistory.setId(chatHistoryDTO.getId());
		chatHistory.setSenderId(chatHistoryDTO.getSenderId());
		chatHistory.setReceiverId(chatHistoryDTO.getReceiverId());
		chatHistory.setText(chatHistoryDTO.getText());
		chatHistory.setReadStatus(chatHistoryDTO.getReadStatus());
		chatHistory.setStatus(chatHistoryDTO.getStatus());
		chatHistory.setCreatedDate(chatHistoryDTO.getCreatedDate());
		chatHistory.setCreatedBy(chatHistoryDTO.getCreatedBy());
		chatHistory.setUpdatedDate(chatHistoryDTO.getUpdatedDate());
		chatHistory.setUpdatedBy(chatHistoryDTO.getUpdatedBy());

		return chatHistory;
	}

	public static ChatHistoryDTO getChatHistoryDTOByChatHistory(ChatHistory chatHistory) {
		ChatHistoryDTO dto = new ChatHistoryDTO();

		dto.setId(chatHistory.getId());
		dto.setSenderId(chatHistory.getSenderId());
		dto.setReceiverId(chatHistory.getReceiverId());
		dto.setText(chatHistory.getText());
		dto.setReadStatus(chatHistory.getReadStatus());
		dto.setStatus(chatHistory.getStatus());
		dto.setCreatedDate(chatHistory.getCreatedDate());
		dto.setCreatedBy(chatHistory.getCreatedBy());
		dto.setUpdatedDate(chatHistory.getUpdatedDate());
		dto.setUpdatedBy(chatHistory.getUpdatedBy());

		return dto;
	}

}
