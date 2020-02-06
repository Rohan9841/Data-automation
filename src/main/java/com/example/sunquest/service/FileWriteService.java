package com.example.sunquest.service;

import java.io.IOException;
import java.util.Map;

public interface FileWriteService {
	Map<String, Double>  writeFile(Map<String, Double> data) throws IOException;
}
