    package com.example.CRM.FIOStream.init;

    import org.apache.poi.ss.usermodel.*;

    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.Random;

    public class initExcelTest {
        // Columns for the Excel file
        public static final String[] COLUMNS = {"Email", "First Name", "Last Name", "Phone", "Date of Birth"};
        public static final String FILE_NAME = "CustomerTestData.xlsx";


        public static void createHeaderRow(Workbook workbook, Sheet sheet) {
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < COLUMNS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMNS[i]);
                cell.setCellStyle(headerStyle);
            }
        }

        /**
         * Generate 100 rows of test data.
         */
        public static void generateTestData(Sheet sheet) {
            Random random = new Random();
            for (int i = 1; i <= 100; i++) {
                Row row = sheet.createRow(i);

                // Email
                String email = "user" + i + "@example.com";
                row.createCell(0).setCellValue(email);

                // First Name
                String firstName = "FirstName" + random.nextInt(100);
                row.createCell(1).setCellValue(firstName);

                // Last Name
                String lastName = "LastName" + random.nextInt(100);
                row.createCell(2).setCellValue(lastName);

                // Phone
                String phone = String.format("%010d", random.nextInt(1_000_000_000));
                row.createCell(3).setCellValue(phone);

                // Date of Birth
                LocalDate dob = LocalDate.of(1980 + random.nextInt(30), 1 + random.nextInt(12), 1 + random.nextInt(28));
                row.createCell(4).setCellValue(dob.toString());
            }
        }

        /**
         * Autosize all columns in the sheet.
         */
        public static void autoSizeColumns(Sheet sheet) {
            for (int i = 0; i < COLUMNS.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        /**
         * Write workbook to an Excel file.
         */
        public static void writeToFile(Workbook workbook) {
            try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
                workbook.write(fileOut);
                System.out.println("Excel file created: " + FILE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
