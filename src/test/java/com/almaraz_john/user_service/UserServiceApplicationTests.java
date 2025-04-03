package com.almaraz_john.user_service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceApplicationTests {

	@BeforeAll
	static void init() {
		System.setProperty("spring.profiles.active", "test");
	}

	@Test
	void contextLoads() {
	}
	@Test
	void mainMethodTest() {
		UserServiceApplication.main(new String[]{});
	}
}
