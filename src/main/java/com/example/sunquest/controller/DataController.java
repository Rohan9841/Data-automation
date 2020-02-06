package com.example.sunquest.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sunquest.model.APIresponse;
import com.example.sunquest.service.FileReadService;
import com.example.sunquest.service.FileWriteService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/admin", produces = "application/json")
public class DataController {

	@Autowired
	FileReadService fileReadService;
	
	@Autowired 
	FileWriteService fileWriteService;

	@PostMapping(path = "/uploadFile")
	public APIresponse UploadDataFile(@RequestParam("file") MultipartFile file) {

		Map<String, Double> data;
		Map<String, Double> output;

		try {
			data = fileReadService.readFile(file);
			
			if(data == null) {
				return new APIresponse(HttpStatus.FORBIDDEN.value(), "Something went wrong while reading data", null);
			}
			
			output = fileWriteService.writeFile(data);
			
			if(output == null) {
				return new APIresponse(HttpStatus.FORBIDDEN.value(), "Something went wrong while writing data", null);
			}
			
			return new APIresponse(HttpStatus.OK.value(), "Excel file was successfully read and updated.", data);
		} catch (IOException e) {
			return new APIresponse(HttpStatus.FORBIDDEN.value(), "There was some error during file reading.", null);
		}
	}
}
