package com.design.patterns.proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class PartnerUpload implements BasePartnerUpload {
	
	/**
	 * @return Map<Integer,List<ExcelData>> --> <<tt>Key as Row number,
	 *  in each row have multiple Column name and Column value</tt>
	 */
	@Override
	public Map<Integer, List<ExcelData>> parse(File file) throws Exception {
		if(file==null) {
			throw new IllegalArgumentException("file doesn't exist");
		}
		Map<Integer,List<ExcelData>> excelDataMap=new HashMap<>();
		Workbook workbook=WorkbookFactory.create(file);
		//One excel-sheet can have multiple sheets
		if(workbook!=null && workbook.getNumberOfSheets()>0 ) {
			DataFormatter dataFormatter=new DataFormatter();
			
			for(int i=0;i<workbook.getNumberOfSheets();i++) {
				List<String> columnNames = new ArrayList<>();
				//Got first sheet, Now parsing sheet
				Sheet sheet=workbook.getSheetAt(i);
				System.out.println("Parsing sheet: "+i+1);
				//All Row in sheet i
				Iterator<Row> rows=sheet.iterator();
				//Position 1 means column name
				int position=1;
				int rowNumber=0;
				while(rows.hasNext()) {
					Row row=rows.next();
					List<ExcelData> rowData=new ArrayList<>();
					//Each row can have multiple Column
					Iterator<Cell> cells=row.cellIterator();
					int columnNumber=0;
					while(cells.hasNext()) {
						Cell cell=cells.next();
						if(position==1) {
							columnNames.add(dataFormatter.formatCellValue(cell));
						}
						else {
							ExcelData excelData=new ExcelData();
							excelData.setColumnName(columnNames.get(columnNumber++));
							excelData.setColumnValue(dataFormatter.formatCellValue(cell));
							rowData.add(excelData);
						}
					}
					position++;
					excelDataMap.put(rowNumber++, rowData);
				}
			}
		}
		excelDataMap.remove(0);
		return excelDataMap;
	}

	@Override
	public void displayData(Map<Integer, List<ExcelData>> parsedData) throws Exception {
		if(parsedData!=null && !parsedData.isEmpty()) {
			Set<Integer> rowNumbers=parsedData.keySet();
			for(Integer rowNumber:rowNumbers) {
				List<ExcelData> columnData=parsedData.get(rowNumber);
				System.out.println("Row Number --> "+rowNumber);
				for(ExcelData columnInfo:columnData) {
					System.out.println("Column Name: "+columnInfo.getColumnName()+" -- Column Value: "+columnInfo.getColumnValue());
				}
				System.out.println("Row Number ---> "+rowNumber+ " Ended ");
			}
		}
	}
}
