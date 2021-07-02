package com.design.patterns.genericsort;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Employee {
    private String id;
    private String name;
    private int age;
}
