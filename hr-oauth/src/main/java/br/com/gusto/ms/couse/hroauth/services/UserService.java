package br.com.gusto.ms.couse.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gusto.ms.couse.hroauth.entities.User;
import br.com.gusto.ms.couse.hroauth.feignclients.UserFeignClient;

@Service
public class UserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class.getSimpleName());
	
	private final UserFeignClient client;

	@Autowired
	public UserService(UserFeignClient client) {
		this.client = client;
	}
	
	public User findByEmail(String email) {
		User user = client.findByEmail(email).getBody();
		if (user == null) {
			logger.error("Email not found {}", email);
			throw new IllegalArgumentException("Email not found");
		}
		return user;
	}

}