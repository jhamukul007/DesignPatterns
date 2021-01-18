package com.design.patterns.singleton;

public class SingletonUsingHelper {
	private static class SingletonHelper{
		private static final SingletonUsingHelper INS=new SingletonUsingHelper();
	}
	/**
	 * Helper 
	 */
	public static SingletonUsingHelper getInstance() {
		return SingletonHelper.INS;
	}
}
