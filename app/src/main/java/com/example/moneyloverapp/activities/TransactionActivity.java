package com.example.moneyloverapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.CategoryDAO;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Category;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.Spinner.CategorySpinnerAdapter;
import com.example.moneyloverapp.recycleViews.Spinner.WalletSpinnerAdapter;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;

import java.util.Calendar;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Spinner categorySpinner;
    private Spinner walletSpinner;
    TextView amountInput;
    TextView descriptionInput;
    CategoryDAO categoryDAO;
    WalletDAO walletDAO;
    TransactionDAO transactionDAO;
    private Button dateButton;
    private Button saveButton;
    private TextView actionBarTitle;
    private Wallet wallet;
    private Category category;
    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //
        actionBarTitle = findViewById(R.id.transaction_content);
        saveButton = findViewById(R.id.save_btn);
        dateButton = findViewById(R.id.datePickerButton);
        categorySpinner = findViewById(R.id.categorySpinner);
        walletSpinner = findViewById(R.id.walletSpinner);
        //
        categoryDAO = new CategoryDAO(getApplicationContext());
        walletDAO = new WalletDAO(getApplicationContext());
        transactionDAO = new TransactionDAO(getApplicationContext());
        //
        initDatePicker();
        dateButton.setText(getTodaysDate());

        //category spinner
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(), categoryDAO.GetAll());
        categorySpinner.setAdapter(categorySpinnerAdapter);
        //wallet spinner
        List<Wallet> wallets = walletDAO.GetAll();
        wallets.remove(0);
        WalletSpinnerAdapter walletSpinnerAdapter = new WalletSpinnerAdapter(getApplicationContext(), wallets);
        walletSpinner.setAdapter(walletSpinnerAdapter);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            actionBarTitle.setText(intent.getStringExtra("actionBarTitle"));

            if(intent.getStringExtra("transactionId") != null){
                int id = intent.getIntExtra("transactionId", 0);

                transaction = transactionDAO.GetById(id);
                if(transaction != null){
                    category = categoryDAO.GetById(transaction.getCategoryId());
                    wallet = walletDAO.GetById(transaction.getWalletId());

                     TextView amount = findViewById(R.id.tran)
                }
            }
        }

        //exit button
        ImageView backImg = findViewById(R.id.exit_button);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //button save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView amountTV = findViewById(R.id.amount_input);
                TextView idTV = findViewById(R.id.transaction_id);
                TextView descriptionTV = findViewById(R.id.description_input);

                if(amountTV.getText() == null || amountTV.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    TransactionDAO transactionDAO = new TransactionDAO(getApplicationContext());
                    Transaction transaction = new Transaction();
                    transaction.setAmount(Float.parseFloat(amountTV.getText().toString()));
                    transaction.setCategoryId(Integer.parseInt(((TextView)categorySpinner.findViewById(R.id.category_id)).getText().toString()));
                    transaction.setDate(DateTimeUltilities.StringToDate("MMM/dd/yyyy", dateButton.getText().toString()));
                    transaction.setWalletId(Integer.parseInt(((TextView)walletSpinner.findViewById(R.id.sp_wallet_id)).getText().toString()));
                    transaction.setDescription(descriptionTV.getText().toString());

                    transactionDAO.Add(transaction);

                    Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month){
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }

        return "JAN";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

}