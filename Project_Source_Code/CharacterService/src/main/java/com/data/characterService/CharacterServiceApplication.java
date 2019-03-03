package com.data.characterService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class CharacterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharacterServiceApplication.class, args);
	}
	@RequestMapping(value = "/")
	   public String home() {
	      return "Character Service";
	   }
}
