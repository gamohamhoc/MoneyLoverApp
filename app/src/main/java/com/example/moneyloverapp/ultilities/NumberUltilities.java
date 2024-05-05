package com.example.moneyloverapp.ultilities;

import java.text.DecimalFormat;

public class NumberUltilities {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public static String FormatBalance(float balance){
        return decimalFormat.format(balance) + " VND";
    }
}
