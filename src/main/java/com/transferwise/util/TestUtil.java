package com.transferwise.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.transferwise.base.TestBase;

public class TestUtil extends TestBase{

	public static long IMPLICIT_WAIT = 30;
	public static long PAGE_LOAD_TIMEOUT = 60;
	
	public static Object[][] getExcelData(String excelPath, String sheetName) {
		
		log.info("Reading data from input excel file...");
		
		FileInputStream file = null;
		Workbook objWorkbook = null;
		Sheet objSheet;
		Object[][] data = null;
		
		//Read input excel file
		try {
			file = new FileInputStream(excelPath);
		} catch (FileNotFoundException e) {
			log.error("Read excel data failed: Failed to get data from excel file");
			e.printStackTrace();
		}
		
		try {
			objWorkbook = WorkbookFactory.create(file);
		} catch (IOException e) {
			log.error("Read excel data failed: Failed to create workbook factory ");
			e.printStackTrace();
		}
		
		objSheet = objWorkbook.getSheet(sheetName);
		data = new Object[objSheet.getLastRowNum()][objSheet.getRow(0).getLastCellNum()];

		DataFormatter format = new DataFormatter();
		
		for (int rowCounter = 0; rowCounter < objSheet.getLastRowNum(); rowCounter++) {
			for (int cellCounter = 0; cellCounter < objSheet.getRow(0).getLastCellNum(); cellCounter++) {
				data[rowCounter][cellCounter] = format.formatCellValue(objSheet.getRow(rowCounter + 1).getCell(cellCounter));
			}
		}
		
		log.info("Read data from input excel is completed");
		return data;
	}

	public static double TransferWiseConverter(double sAmountToConvert, double sGuaranteedRate) {
		double convertedAmount = 0.0;

		//Calculate expected amount after conversion
		convertedAmount = sAmountToConvert * sGuaranteedRate;

		return Math.round(convertedAmount * 100.0) / 100.0;
	}
}
