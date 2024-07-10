package atsys.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DatetimeUtils {

    public static Instant parseInstant(String input, String pattern){
       return parseInstant(input, pattern, Locale.ENGLISH, ZoneId.systemDefault());
    }

    public static Instant parseInstant(String input, String pattern, Locale locale){
        return parseInstant(input, pattern, locale, ZoneId.systemDefault());

    }

    public static Instant parseInstant(String input, String pattern, ZoneId zoneId){
        return parseInstant(input, pattern, Locale.ENGLISH, zoneId);

    }

    public static Instant parseInstant(String input, String pattern, Locale locale, ZoneId zoneId){
        LocalDate localDate = LocalDate.parse(input, DateTimeFormatter.ofPattern(pattern, locale));
        ZonedDateTime zonedDateTime = localDate.atStartOfDay().atZone(zoneId);
        return zonedDateTime.toInstant();
    }
}
