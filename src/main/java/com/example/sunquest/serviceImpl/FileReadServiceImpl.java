package com.example.sunquest.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sunquest.service.FileReadService;

@Service(value = "fileReadService")
public class FileReadServiceImpl implements FileReadService {

	@Override
	public Map<String, Double> readFile(MultipartFile file) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
		
		HSSFSheet worksheet = workbook.getSheetAt(2);

		if (isSheetFull(worksheet) == true) {

			Map<String, Double> aMap = new HashMap<>();
			DataFormatter formatter = new DataFormatter();

			for (int i = 6; i < worksheet.getLastRowNum(); i++) {

				HSSFRow row = worksheet.getRow(i);

				if (isRowEmpty(row) == false) {
					
					HSSFCell codeCell = row.getCell(0);
					HSSFCell amountCell = row.getCell(14);
					
					
					HSSFRow belowRow = worksheet.getRow(i+1);
					
					if(isRowEmpty(belowRow) == false) {
						String code = formatter.formatCellValue(codeCell);
						Double amount = amountCell.getNumericCellValue();
						aMap.put(code, amount);
					}
				}
			}

			return aMap;
		}

		return null;
	}
	
	public boolean isSheetFull(HSSFSheet worksheet) {
		Iterator<?> rows = worksheet.rowIterator();
		while (rows.hasNext()) {
			HSSFRow row = (HSSFRow) rows.next();
			Iterator<?> cells = row.cellIterator();
			while (cells.hasNext()) {
				HSSFCell cell = (HSSFCell) cells.next();
				if (!cell.getStringCellValue().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isRowEmpty(HSSFRow row) {
		if (row == null || row.getLastCellNum() <= 0) {
			return true;
		}
		HSSFCell cell = row.getCell((int) row.getFirstCellNum());
		if (cell == null || "".equals(cell.getRichStringCellValue().getString())) {
			return true;
		}
		return false;
	}

	public boolean isCellEmpty(List<HSSFCell> cellList) {

		for (HSSFCell cell : cellList) {
			if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
					|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING && cell.getStringCellValue().trim().isEmpty())) {
				return true;
			}
		}
		return false;
	}

}
