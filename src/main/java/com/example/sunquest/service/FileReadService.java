package com.example.sunquest.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface FileReadService {
	
	Map<String, Double>  readFile(MultipartFile file) throws IOException;

}
