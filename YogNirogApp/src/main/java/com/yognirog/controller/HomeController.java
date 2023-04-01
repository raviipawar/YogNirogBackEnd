package com.yognirog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/welcome")
	public String welcome() {
		String msg = "private page";
		msg += "my home page";
		return msg;
	}
}
