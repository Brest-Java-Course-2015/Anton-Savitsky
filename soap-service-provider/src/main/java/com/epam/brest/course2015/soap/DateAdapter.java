package com.epam.brest.course2015.soap;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateAdapter {
  public static LocalDate parseDate(String s) {
    DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    return DATE_FORMAT.parseLocalDate(s);
  }
  public static String printDate(LocalDate ld) {
    return ld.toString("yyyy-MM-dd");
  }
}