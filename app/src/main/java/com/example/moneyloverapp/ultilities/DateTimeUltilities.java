package com.example.moneyloverapp.ultilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUltilities {

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String FormatDate(String format, Date date){
        sdf.applyPattern(format);

        return sdf.format(date);
    }

}
