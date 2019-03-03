package com.data.characterService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterServiceController {
	@RequestMapping(value = "/")
	   public String home() {
	      return "Character Service";
	   }
}
