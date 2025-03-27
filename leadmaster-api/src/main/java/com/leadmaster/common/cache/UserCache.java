package com.leadmaster.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.User;
import com.leadmaster.common.repository.UserRepository;

@Component
public class UserCache {

	private Logger LOGGER = LoggerFactory.getLogger(UserCache.class);

	@Autowired
	private UserRepository userRepository;

	@Cacheable(value = "usersCache", key = "#p0")
	public User getUser(Long id) {
		if (null == id) {
			LOGGER.error("User not present in the system:" + id);
			return null;
		}
		return userRepository.findById(id).get();
	}
}
