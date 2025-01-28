package com.progskipper.bookmarks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookmarksApplication {


	/// Main Function
	public static void main(String[] args) {
		SpringApplication.run(BookmarksApplication.class, args);
	}

	/// Making RestTemplate Available
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();

	}
}


