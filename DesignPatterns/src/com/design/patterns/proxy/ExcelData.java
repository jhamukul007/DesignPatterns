package com.design.patterns.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public @Data class ExcelData {
	private String columnName;
	private String columnValue;
}
