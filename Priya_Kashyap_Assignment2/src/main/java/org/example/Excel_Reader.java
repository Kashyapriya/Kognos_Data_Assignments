package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Excel_Reader {

    public List<SearchResult> searchExcelFile(String filePath, String keywords) {
        List<SearchResult> results = new ArrayList<>(); //For the search results we find
        try (FileInputStream fis = new FileInputStream(new File(filePath)); //For opening the file
             Workbook workbook = new XSSFWorkbook(fis)) { //to work with excel file
        	
        	//Loop to work with workbook

            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String cellValue = cell.toString();
                        
                        //To check if the cell conains the keyword or not
                        
                        if (Arrays.stream(keywords.split(","))
                                .map(String::trim)
                                .anyMatch(cellValue::contains)) {
                        	
                        	//if true add a new SearchResult
                            results.add(new SearchResult(cellValue, row.getRowNum(), cell.getColumnIndex()));
                        }
                    }
                }
            }
        } catch (IOException e) { //To catch any file handelling exception
            e.printStackTrace();
        }
        return results; //to return the results
    }
}

