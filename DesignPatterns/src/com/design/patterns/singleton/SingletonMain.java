package com.design.patterns.singleton;

import java.util.List;

public class SingletonMain {
	public enum Type{
		EAGER,LAZY,HELPER;
	}
	
	public static void main(String[] args) {
		
		//Eager 
		EnumUtils ins=EnumUtils.utils;
		String str="LAZY,EAGER,HELPER";
		List<Enum<?>> enumList=ins.convertToStringEnumList(Type.class, str, ",");
		System.out.println(enumList);	
		String s="LAZY";
		Type type=(Type)ins.convertStringToEnum(Type.class, s);
		System.out.println(type);
		
		//Lazy Thread unsafe
		EnumUtils ins1=EnumUtils.getInstance();
		System.out.println("Thread Unsafe ins1 hash: "+ins1.hashCode());
		
		EnumUtils ins2=EnumUtils.getInstance();
		System.out.println("Thread Unsafe ins1 hash: "+ins2.hashCode());
		
		//Refernce Checking in memory
		System.out.println("Both thread unsafe object share same object from heap: "+(ins1==ins2));
		
		//Lazy Thread Safe
		EnumUtils ins3=EnumUtils.getThreadSafeInstance();
		System.out.println("Thread Safe ins1 hash: "+ins3.hashCode());
		
		EnumUtils ins4=EnumUtils.getThreadSafeInstance();
		System.out.println("Thread Safe ins2 hash: "+ins4.hashCode());
		
		//Refernce Checking in memory
		System.out.println("Both thread safe object share same object from heap: "+(ins3==ins4));
		
	}
}
