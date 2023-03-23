package com.homebrewtify.demo;

import com.homebrewtify.demo.controller.GenreController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private GenreController genreController;

	@Test
	void contextLoads() {
		assert genreController != null;
	}

}
