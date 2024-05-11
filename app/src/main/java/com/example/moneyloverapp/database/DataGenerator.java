package com.example.moneyloverapp.database;

import android.database.sqlite.SQLiteDatabase;

import kotlin.jvm.internal.PropertyReference0Impl;

public class DataGenerator {

    private final static String INSERT_WALLETS =
            "INSERT INTO Wallets(Name, Balance, IsIncluded)" +
            "VALUES" +
                "('Total', 13780000, 1)," +
                "('Lương', 7780000, 1)," +
                "('Bố mẹ chu cấp', 4000000, 1)," +
                "('Dạy thêm', 2000000, 0)";

    private final static String INSERT_CATEGORIES =
            "INSERT INTO Categories(Name, Type)" +
            "VALUES" +
            //thu
                "('Bán đồ', '1')," +
                "('Được tặng', '1')," +
                "('Lương', '1')," +
                "('Thưởng', '1')," +
                "('Tiền lãi', '1')," +
                "('Thu nhập khác', '1')," +
            //chi
                "('Ăn uống', '0')," +
                "('Bạn bè', '0')," +
                "('Người yêu', '0')," +
                "('Di chuyển', '0')," +
                "('Gia đình', '0')," +
                "('Du lịch', '0')," +
                "('Giải trí', '0')," +
                "('Giáo dục', '0')," +
                "('Mua sắm', '0')," +
                "('Cưới hỏi', '0')," +
                "('Tang lễ', '0')," +
                "('Từ thiện', '0')," +
                "('Các chi phí khác', '0')";

    private final static String INSERT_TRANSACTIONS =
            "INSERT INTO Transactions(CategoryId, WalletId, Amount, Date, Description)" +
            "VALUES" +
                "(7, 2, -120000, '25/04/2024', 'Ăn bún chả'), " +
                "(2, 3, 2000000, '25/04/2024', 'Bố mẹ cho'), " +
                "(3, 2, 6000000, '25/04/2024', 'Lương công ty'), " +
                "(12, 2, -1000000, '05/05/2024', 'Mua đồ ăn'), " +
                "(9, 2, -1000000, '07/05/2024', 'Kỷ niệm yêu nhau 1 năm'), " +
                "(7, 2, -20000, '07/05/2024', 'Ăn sáng'), " +
                "(7, 2, -20000, '08/05/2024', 'Ăn sáng'), " +
                "(7, 2, -60000, '08/05/2024', 'Ăn trưa'), " +
                "(3, 4, 2000000, '11/05/2024', 'Lương dạy thêm') ";

    private final static String INSERT_BUDGET =
            "INSERT INTO Budgets(CategoryId, WalletId, Amount, StartDate, EndDate)" +
            "VALUES" +
                "(12, 2, 5000000, '01/05/2024', '31/05/2024'), " +
                "(7, 2, 4000000, '01/05/2024', '31/05/2024')";

    public static void InsertData(SQLiteDatabase db){
        db.execSQL(INSERT_WALLETS);
        db.execSQL(INSERT_CATEGORIES);
        db.execSQL(INSERT_TRANSACTIONS);
        db.execSQL(INSERT_BUDGET);
    }
}
