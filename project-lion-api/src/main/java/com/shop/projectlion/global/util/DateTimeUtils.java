package com.shop.projectlion.global.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date convertToDate(LocalDateTime localDateTimeToConvert) {
        Instant instant = localDateTimeToConvert.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

}