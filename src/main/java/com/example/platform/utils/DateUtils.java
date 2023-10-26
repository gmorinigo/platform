package com.example.platform.utils;

import com.example.platform.exceptions.InvalidDateFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateUtils {

    public static LocalDateTime parseStringToLocalDateTime(String date){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss")).toFormatter();
        LocalDateTime dateWithFormat;
        try {

            dateWithFormat = LocalDateTime.parse(date, formatter);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new InvalidDateFormatException();
        }
        return dateWithFormat;
    }
}
