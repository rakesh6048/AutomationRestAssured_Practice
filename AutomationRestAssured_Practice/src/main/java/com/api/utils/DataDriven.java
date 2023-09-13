package com.api.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	public ArrayList<String> getData(String testCaseName, String Sheets) throws IOException {
        
		ArrayList<String> a= new ArrayList<String>();
		
         FileInputStream fis= new FileInputStream(System.getProperty("user.dir") + "\\Testdata.xlsx");
         XSSFWorkbook workbook = new XSSFWorkbook(fis);
         
         int sheets=workbook.getNumberOfSheets();
         for(int i=0;i<sheets;i++) {
        	 if(workbook.getSheetName(i).equalsIgnoreCase(Sheets)) {
        		 XSSFSheet sheet = workbook.getSheetAt(i);
        		 //identyfy Testcases column by scanning the entire 1st row
        		 
        		 Iterator<Row> rows = sheet.iterator(); //sheet is collection of rows
        		 Row firstrow = rows.next();
        		 //Row secondrow = rows.next();
        		 Iterator<Cell> cell=firstrow.cellIterator(); //row is collection of cells
        		 int k=0;
        		 int coloumn =0;
        		 //Traverse all the coloum cell of start from first cell
        		 while(cell.hasNext()) {
        			Cell value=cell.next();
        			if(value.getStringCellValue().equalsIgnoreCase("Testcases")) {
        				System.out.println("Coloumn Name : "+value.getStringCellValue());
        				coloumn=k;
        				System.out.println("Coloum Index Expected Cell : "+coloumn);
        				
        			}
        			k++;
        		 }
        		 System.out.println("coloumn index : "+coloumn);
        		 
        		//once column is identified then scan entire testcase coloum to identyfy purchase testcase row 
        		 
        		 //Traverse the all row in each cell
        		 System.out.println("Print all coloumn cell value basis on the column");
        		 System.out.println("In Particular cell print all row cell value");
        		 while(rows.hasNext()) {
        			 
        			 Row r=rows.next();
        			//System.out.println(r.getCell(coloumn).getStringCellValue());
        			 if(r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testCaseName)) {
        				
        		     //after you grab purchase testcase row = pull all the data of that row and feed
        				 
        				 Iterator<Cell> cv=r.cellIterator();
        				 while(cv.hasNext()) {
        					Cell c =cv.next();
        					
        					if(c.getCellTypeEnum()==CellType.STRING) {
        					
        					a.add(c.getStringCellValue());
        					
        					}else{
        			
        					   a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
        					}
        				 }		 
        				 
        			 }
        		 }
        		 
        	 }
         }
         
         return a;
	}
	
}