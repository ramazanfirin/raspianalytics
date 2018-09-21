package com.customer.customer.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.customer.model.ResultVM;
import com.customer.customer.service.AnalyzeService;

@RestController
@RequestMapping("/api")
public class AnalyseController {

	@Autowired
	AnalyzeService analyzeService;
	
	@GetMapping("/analyze")
    public ResultVM analyze(@RequestParam("path") String path) throws URISyntaxException {
		ResultVM resultVM = analyzeService.findAgeGender(path);
		return resultVM;
    }

}
