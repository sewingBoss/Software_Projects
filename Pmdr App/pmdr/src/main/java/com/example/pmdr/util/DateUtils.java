package com.example.pmdr.util;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class DateUtils {
    public static OffsetDateTime parseDate(String dateStr) {
        OffsetDateTime dateTime;
        try {
            dateTime = OffsetDateTime.parse(dateStr);
        } catch(DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(dateStr);
                dateTime = date.atStartOfDay().atOffset(ZoneOffset.UTC);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("System received invalid date format", e2);
            }
        }
        return dateTime;
    }
}