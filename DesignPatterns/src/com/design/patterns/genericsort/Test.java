package com.design.patterns.genericsort;

import java.util.Arrays;
import java.util.List;


public class Test {

    private static void print(List<Employee> emps, boolean isName){
        if(emps==null || emps.isEmpty())
            return;
        for(Employee emp:emps){
            if(isName)
                System.out.println(emp.getName());
            else
                System.out.println(emp.getAge());
        }
    }
    public static void main(String[] args) {
        Employee emp=new Employee();
        emp.setName("Muk");

        Employee emp1=new Employee();
        emp1.setName("jack");

        Employee emp3=new Employee();
        emp3.setName("jadu");
        List<Employee> list= Arrays.asList(emp,emp1,emp3);
        System.out.println("UnSorted Array");
        print(list,true);
        Sort<Employee> ins=new Sort.SortBuilder<Employee>()
                .setSortBy(Sort.SortBy.NAME)
                .setSortOrder(Sort.SortOrder.ASC)
                .build();
        ins.sort(list);
        System.out.println("------------Sorted list--------");
        print(list, true);

        emp.setAge(15);
        emp1.setAge(8);
        emp3.setAge(10);
        List<Employee> emps=Arrays.asList(emp,emp1,emp3);
        print(emps,false);
        Sort<Employee> ins1=new Sort.SortBuilder<Employee>()
                .setSortBy(Sort.SortBy.NAME)
                .setSortOrder(Sort.SortOrder.DESC)
                .build();
        ins1.sort(emps,"age");
        System.out.println("------------");
        print(emps,false);
    }
}
