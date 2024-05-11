package com.example.moneyloverapp.ultilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUltilities {

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String FormatDate(String format, Date date){
        sdf.applyPattern(format);

        return sdf.format(date);
    }

    public static Date StringToDate(String format, String date){
        sdf.applyPattern(format);
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            return new Date(2002 + 1900, 9, 21);
        }
        return date1;
    }
}
