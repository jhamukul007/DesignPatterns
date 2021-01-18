package com.design.patterns.singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnumUtils implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8624130803055954062L;
	
	//eager initialization
	public static final EnumUtils utils=new EnumUtils();
	private static EnumUtils ins;
	
	/**
	 * Convert list enum to list of <tt>String</tt>
	 * @param list of emum
	 * @return list of String
	 */
	public List<String> convertEnumtoStringList(List<? extends Enum> list){
		if(list==null || list.isEmpty())
			return Collections.emptyList();
		List<String> dataList=new ArrayList<>();
		for(Enum d :list) {
			dataList.add(d.name());
		}
		return dataList;
	}
	
	public Enum<?> convertStringToEnum(final Class<? extends Enum> type,String str){
		List<Enum<?>> enums=convertToStringEnumList(type, str, ",");
		if(enums!=null) {
			return enums.get(0);
		}
		return null;
	}
	
	/**
	 * convert String 
	 * @param type: Enum Name
	 * @param str 
	 * @param seperator
	 */
	@SuppressWarnings("unchecked")
	public static List<Enum<?>> convertToStringEnumList(final Class<? extends Enum> type,String str,String seperator){ 
		if(str==null || str=="")
			return null;
		if(seperator==null || seperator=="")
			throw new RuntimeException("Seperator can't be null");
		String[] enumStrs=str.split("\\"+seperator);
		List<Enum<?>> enumList=new ArrayList<>();
		
		for(String enumStr:enumStrs) {
			enumList.add(Enum.valueOf(type, enumStr));
		}
		return enumList;
	}
	
	/**
	 * Lazy, Not Thread Safe 
	 */
	public static EnumUtils getInstance() {
		if(ins==null) {
			ins=new EnumUtils();
		}
		return ins;
	}
	public static EnumUtils getThreadSafeInstance() {
		if(ins==null) {
			synchronized (EnumUtils.class) {
				if(ins==null) {
					ins=new EnumUtils();
				}
			}
		}
		return ins;
	}
	
	@Override
	public EnumUtils clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
}
