package com.design.patterns.builder;

import java.util.Arrays;
import java.util.List;

public class BuilderMain {
	public static void main(String[] args) {
		List<String> lists=Arrays.asList("Mukul","Muk","M","U","UL","ukul","jha","jh");
		RandomGenerator<String> generator=new RandomGenerator.RandomGeneratorBuilder<String>()
				.setData(lists).build();
		
		//Get Random From the list
		System.out.println("First random : "+generator.getRandom());
		System.out.println("2nd random:"+generator.getRandom());
	
		//Use same Instance to get Random name
		System.out.println("Get auto generated name: "+generator.getRandomIdentifier());
		
		//Or Use other instance
		RandomGenerator<String> generator2=new RandomGenerator
				.RandomGeneratorBuilder<String>()
				.build();
		System.out.println("Different ins auto generated value "+generator2.getRandomIdentifier());
		
		
	}
}
