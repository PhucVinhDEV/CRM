package com.example.CRM.FIOStream.Service;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.CustomerEAV.repository.CustomerRepository;
import com.example.CRM.common.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public interface ImportFileService {
//    List<Customer> parseDataToCustomerRecords(MultipartFile file);
}

@Service
@RequiredArgsConstructor
class ImportFileServiceImpl implements ImportFileService {

    private final CustomerRepository customerRepository;
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList("csv", "xlsx"));
    private static final Set<String> ALLOWED_MIME_TYPES = new HashSet<>(Arrays.asList(
            "text/csv",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    ));
    private static final Logger log = LoggerFactory.getLogger(ImportFileServiceImpl.class);

//    @Override
//    public List<Customer> parseDataToCustomerRecords(MultipartFile file) {
//        validateFile(file);
//
////        // Existing parsing logic based on file type
////        if (isCSVFile(file)) {
////            return customerRepository.saveAll(parseCSVFile(file));
////        } else if (isXLSXFile(file)) {
////            return customerRepository.saveAll(parseXLSXFile(file));
////        }
//
//        throw new IllegalArgumentException("Unsupported file format");
//    }
    private String validateAndGetEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email;
    }

    private String validateAndGetString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value.trim();
    }

    private String validateAndGetPhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number format. Must be 10 digits");
        }
        return phone;
    }

    private LocalDate validateAndGetDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeUtil.DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }
    }
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Invalid filename");
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        String mimeType = file.getContentType();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Only CSV and XLSX files are allowed");
        }

        if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType.toLowerCase())) {
            throw new IllegalArgumentException("Invalid file type. Only CSV and XLSX files are allowed");
        }
    }

    private boolean isCSVFile(MultipartFile file) {
        String mimeType = file.getContentType();
        return mimeType != null && mimeType.equals("text/csv");
    }

    private boolean isXLSXFile(MultipartFile file) {
        String mimeType = file.getContentType();
        return mimeType != null &&
                mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return filename.substring(lastDotIndex + 1);
        }
        throw new IllegalArgumentException("File must have an extension");
    }

    private Map<String, Integer> createColumnMap(Row headerRow) {
        Map<String, Integer> columnMap = new HashMap<>();

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                String headerName = cell.getStringCellValue().trim();
                columnMap.put(headerName, i);
            }
        }

        return columnMap;
    }

    private void validateRequiredColumns(Map<String, Integer> columnMap) {
        List<String> requiredColumns = Arrays.asList(
                "Email", "First Name", "Last Name", "Phone", "Date of Birth"
        );

        List<String> missingColumns = requiredColumns.stream()
                .filter(col -> !columnMap.containsKey(col))
                .collect(Collectors.toList());

        if (!missingColumns.isEmpty()) {
            throw new IllegalArgumentException("Missing required columns: " + String.join(", ", missingColumns));
        }
    }

    private String getCellValue(Row row, Integer columnIndex) {
        if (columnIndex == null) {
            return null;
        }

        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                // Handle phone numbers to prevent scientific notation
                double numericValue = cell.getNumericCellValue();
                if (numericValue == (long) numericValue) {
                    return String.format("%.0f", numericValue);
                }
                return String.valueOf(numericValue);
            case BLANK:
                return null;
            default:
                return cell.toString().trim();
        }
    }
//    private List<Customer> parseCSVFile(MultipartFile file) {
//        List<Customer> customers = new ArrayList<>();
//        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
//                     .withFirstRecordAsHeader()
//                     .withIgnoreHeaderCase()
//                     .withTrim())) {
//
//            for (CSVRecord record : csvParser) {
//                try {
//                    Customer customer = Customer.builder()
//                            .email(validateAndGetEmail(record.get("Email")))
//                            .firstName(validateAndGetString(record.get("First Name"), "First Name"))
//                            .lastName(validateAndGetString(record.get("Last Name"), "Last Name"))
//                            .phone(validateAndGetPhone(record.get("Phone")))
//                            .dob(validateAndGetDate(record.get("Date of Birth")))
//                            .build();
//                    log.info("Add " + customer.getEmail());
//                    customers.add(customer);
//                } catch (IllegalArgumentException e) {
//                    // Log the error and continue with next record
//                    System.err.println("Error processing record: " + record.toString() + ". Error: " + e.getMessage());
//                }
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
//        }
//        return customers;
//    }

    private List<Customer> parseXLSXFile(MultipartFile file) {
        List<Customer> customers = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Get first sheet

            // Get header row
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnMap = createColumnMap(headerRow);

            // Validate required columns exist
            validateRequiredColumns(columnMap);

            // Iterate through data rows
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                try {
                    Customer customer = Customer.builder()
                            .email(validateAndGetEmail(getCellValue(row, columnMap.get("Email"))))
                            .firstName(validateAndGetString(getCellValue(row, columnMap.get("First Name")), "First Name"))
                            .lastName(validateAndGetString(getCellValue(row, columnMap.get("Last Name")), "Last Name"))
                            .phone(validateAndGetPhone(getCellValue(row, columnMap.get("Phone"))))
                            .dob(validateAndGetDate(getCellValue(row, columnMap.get("Date of Birth"))))
                            .build();
                    log.info("Add " + customer.getEmail());
                    customers.add(customer);
                } catch (IllegalArgumentException e) {
                    // Log the error and continue with next record
                    System.err.println("Error processing row " + (rowNum + 1) + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XLSX file: " + e.getMessage(), e);
        }

        return customers;
    }
}