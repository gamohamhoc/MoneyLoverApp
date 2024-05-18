package com.example.moneyloverapp.ultilities;

import java.text.DecimalFormat;

public class NumberUltilities {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private static final DecimalFormat decimalFormatWithoutComma = new DecimalFormat("###");

    public static String FormatBalanceWithCurrency(float balance){
        return decimalFormat.format(balance) + " VND";
    }

    public static String FormatBalance(float balance){
        return decimalFormatWithoutComma.format(balance);
    }
}
