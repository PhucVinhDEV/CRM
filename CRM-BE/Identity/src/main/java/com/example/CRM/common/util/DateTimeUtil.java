package com.example.CRM.common.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@UtilityClass
public class DateTimeUtil {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FLEXIBLE_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("[yyyy/MM/dd]") // Định dạng 1
            .appendPattern("[yyyy-MM-dd]") // Định dạng 2
            .appendPattern("[dd/MM/yyyy]") // Định dạng 3
            .toFormatter();

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, FLEXIBLE_DATE_FORMATTER);
    }
    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

}
