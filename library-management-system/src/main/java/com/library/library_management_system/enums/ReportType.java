package com.library.library_management_system.enums;

public enum ReportType {
SALARY("Salary Report", "Báo cáo lương"),
READER("Reader Report", "Báo cáo độc giả"),
BOOK("Book Report", "Báo cáo sách"),
BORROW("Borrow Report", "Báo cáo mượn sách");
private final String code;
private final String description;
ReportType(String code, String description) {
this.code = code;
this.description = description;
}
public String getCode() {
return code;
}
public String getDescription() {
return description;
}
}