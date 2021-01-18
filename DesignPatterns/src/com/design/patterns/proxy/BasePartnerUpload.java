package com.design.patterns.proxy;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface BasePartnerUpload {
	Map<Integer,List<ExcelData>> parse(File file) throws Exception;
	void displayData(Map<Integer,List<ExcelData>> excelData) throws Exception;
}
