package com.design.patterns.singleton;

public class SingletonUsingHelperMain {
	public static void main(String[] args) {
		SingletonUsingHelper ins1=SingletonUsingHelper.getInstance();
		System.out.println("Ins1 hashcode: "+ins1.hashCode());
		SingletonUsingHelper ins2=SingletonUsingHelper.getInstance();
		System.out.println("Ins2 hascode: "+ins2.hashCode());
		System.out.println(String.format("Hash Code compare : %s ",ins1.hashCode()==ins2.hashCode()));
		System.out.println(String.format("Both Instance Refering to same object in heap: %s ",ins1==ins2));
	}
}
