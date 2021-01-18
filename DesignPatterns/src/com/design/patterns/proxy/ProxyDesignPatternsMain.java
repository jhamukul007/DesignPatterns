package com.design.patterns.proxy;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.design.patterns.proxy.PartnerEnums.Partner;

public class ProxyDesignPatternsMain {
	public static void main(String[] args) {
		//Location of excel file extension should be .xlsx, .xls
		File file=new File(System.getProperty("user.dir")+"/ExcelTest.xlsx");
		ExcelDataProxy proxy=new ExcelDataProxy();
		proxy.setPartner(Partner.OYO);
		proxy.setAdmin(true);
		try {
			Map<Integer,List<ExcelData>> parsedData=proxy.parse(file);
			proxy.displayData(parsedData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
