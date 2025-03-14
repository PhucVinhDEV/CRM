package com.example.CRM;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableFeignClients(basePackages = "com.example.CRM.Auth.Oauth2.httpclient")
public class CRMApplication {

	public static void main(String[] args) {
		SpringApplication.run(CRMApplication.class, args);
		System.out.println("http://localhost:8080/bitznomad/swagger");
//		// Create workbook and sheet
//		Workbook workbook = new XSSFWorkbook();
//		Sheet sheet = workbook.createSheet("Customer Data");
//		// Create header row
//		initExcelTest.createHeaderRow(workbook, sheet);
//		// Generate random data for 100 rows
//		initExcelTest.generateTestData(sheet);
//		// Autosize columns
//		initExcelTest.autoSizeColumns(sheet);
//		// Write data to Excel file
//		initExcelTest.writeToFile(workbook);
	}


}
