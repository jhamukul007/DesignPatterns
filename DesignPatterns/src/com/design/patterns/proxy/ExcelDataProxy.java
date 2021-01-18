package com.design.patterns.proxy;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.design.patterns.proxy.PartnerEnums.Partner;

public class ExcelDataProxy implements BasePartnerUpload {
	private BasePartnerUpload partnerUpload;
	private Partner patner;
	private boolean isAdmin=false;
	private static boolean parsedSucessFully=false;
	
	public ExcelDataProxy(Partner partner) {
		this.patner=partner;
	}
	public ExcelDataProxy() {
		// TODO Auto-generated constructor stub
	}
	
	public void setPartner(Partner patner) {
		this.patner=patner;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Override
	public Map<Integer, List<ExcelData>> parse(File file) throws Exception {
		if(file!=null && patner!=null) {
			if(isAdmin) {
				if(partnerUpload==null)
					partnerUpload=new PartnerUpload();
				
				System.out.println("Parsing excel-sheet of patner "+patner.name());
				if(file.getAbsolutePath().endsWith("xlsx") || file.getAbsolutePath().endsWith("xls")) {
					Map<Integer,List<ExcelData>> parsedData= partnerUpload.parse(file);
					parsedSucessFully=true;
					System.out.println("Excel Parsed Sucessfully");
					return parsedData;
				}
				throw new RuntimeException("Please upload excel file only");
			}
			else {
				throw new RuntimeException("User Unauthorised !");
			}
		}
		throw new RuntimeException("Can't upload the file");
	}

	@Override
	public void displayData(Map<Integer, List<ExcelData>> parsedData) throws Exception {
		if(isAdmin) {
			if(parsedSucessFully) {
				System.out.println("----- Display Excel Data ----- ");
				partnerUpload.displayData(parsedData);
			}
		}
		else {
			throw new RuntimeException("User Unauthorised to see parsed data");
		}
	}

}
