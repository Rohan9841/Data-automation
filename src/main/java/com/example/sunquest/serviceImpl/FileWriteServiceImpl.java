package com.example.sunquest.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.example.sunquest.service.FileWriteService;

@Service(value = "fileWriteService")
public class FileWriteServiceImpl implements FileWriteService{

	@Override
	public Map<String, Double> writeFile(Map<String, Double> data) {
		
		String excelFilePath = "C:/Users/josep/Dropbox (Sunquest Property)/Property 2014 - 2018 Fin. Analysis/Data Entry Project/TC 2014-2018 Operating Statement Analysis-copy.xlsx";
		try {
			
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			
			Workbook workbook = WorkbookFactory.create(inputStream);
			
			Sheet worksheet = workbook.getSheet("Seasons");
			
			DataFormatter formatter = new DataFormatter();
			
			for (int i = 6; i < worksheet.getLastRowNum(); i++) {
				
				Row row = worksheet.getRow(i);
				String code = formatter.formatCellValue(row.getCell(0));
				System.out.println(code);
				if(data.containsKey(code)) {
					Double amount = data.get(code);
					row.getCell(3).setCellValue(amount);
				}
			}
			
			inputStream.close();
			
			FileOutputStream outputStream = new FileOutputStream("C:/Users/josep/Dropbox (Sunquest Property)/Property 2014 - 2018 Fin. Analysis/Data Entry Project/TC 2014-2018 Operating Statement Analysis-copy.xlsx");
			workbook.write(outputStream);
			outputStream.close();
			
			return data;
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
	}
	
}
