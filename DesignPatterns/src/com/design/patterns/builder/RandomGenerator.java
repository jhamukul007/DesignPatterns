package com.design.patterns.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class RandomGenerator<T>{
	private Collection<T> data;
	private Random randomGenerator=new Random();
	private final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
	private final Set<String> identifiers = new HashSet<String>();

	private RandomGenerator(RandomGeneratorBuilder<T> builder) {
		this.data=builder.data;
	}
	
	public T getRandom() {
		int index=randomGenerator.nextInt(data.size());
		Iterator<T> itr=data.iterator();
		int count=0;
		while(itr.hasNext()) {
			T t=itr.next();
			if(count==index)
				return t;
			count++;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public T getRandomIdentifier() {
	    StringBuilder builder = new StringBuilder();
	    while(builder.toString().length() == 0) {
	        int length = randomGenerator.nextInt(5)+5;
	        for(int i = 0; i < length; i++) {
	            builder.append(lexicon.charAt(randomGenerator.nextInt(lexicon.length())));
	        }
	        if(identifiers.contains(builder.toString())) {
	            builder = new StringBuilder();
	        }
	    }
	    return (T)builder.toString();
	}
	
	public static class RandomGeneratorBuilder<T>{
		private Collection<T> data;
		
		public RandomGeneratorBuilder<T> setData(Collection<T> data) {
			this.data=data;
			return this;
		}
		
		public RandomGenerator<T> build(){
			return new RandomGenerator<T>(this);
		}
	}
	
}