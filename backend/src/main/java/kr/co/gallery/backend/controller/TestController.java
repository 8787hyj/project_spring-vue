package kr.co.gallery.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping(value = "/api/test")
	public String test() {
		return "test RestAPI";
	}

	@PostMapping("/api/upload-image")
	public String uploadImage(@RequestBody String img) {
		System.out.println(img);
		return "upload success : "+img;
	}
}
