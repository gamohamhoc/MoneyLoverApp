package com.example.moneyloverapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Spinner categorySpinner;
    private CategorySpinnerAdapter categorySpinnerAdapterType0;
    private CategorySpinnerAdapter categorySpinnerAdapterType1;
    private Spinner walletSpinner;
    EditText amountInput;
    EditText descriptionInput;
    CategoryDAO categoryDAO;
    WalletDAO walletDAO;
    TransactionDAO transactionDAO;
    private Button dateButton;
    private Button saveButton;
    private TextView actionBarTitle;
    private Wallet wallet;
    private Wallet globalWallet;
    private Category category;
    private Transaction transaction;
    int transactionId = 0;

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
        amountInput = findViewById(R.id.amount_input);
        descriptionInput = findViewById(R.id.description_input);
        //
        categoryDAO = new CategoryDAO(getApplicationContext());
        walletDAO = new WalletDAO(getApplicationContext());
        transactionDAO = new TransactionDAO(getApplicationContext());
        //
        initDatePicker();
        dateButton.setText(getTodaysDate());

        //category spinner
        categorySpinnerAdapterType1 = new CategorySpinnerAdapter(getApplicationContext(), categoryDAO.GetByType(1));
        categorySpinnerAdapterType0 = new CategorySpinnerAdapter(getApplicationContext(), categoryDAO.GetByType(0));
        categorySpinner.setAdapter(categorySpinnerAdapterType1);
        //radio group
        ((RadioGroup)findViewById(R.id.radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.thuRadio){
                    categorySpinner.setAdapter(categorySpinnerAdapterType1);
                } else if (checkedId == R.id.chiRadio) {
                    categorySpinner.setAdapter(categorySpinnerAdapterType0);
                }
            }
        });
        //wallet spinner
        List<Wallet> wallets = walletDAO.GetAll();
        globalWallet = wallets.get(0);
        wallets.remove(0);
        WalletSpinnerAdapter walletSpinnerAdapter = new WalletSpinnerAdapter(getApplicationContext(), wallets);
        walletSpinner.setAdapter(walletSpinnerAdapter);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            actionBarTitle.setText(intent.getStringExtra("actionBarTitle"));

            int id = intent.getIntExtra("transactionId", 0);
            transactionId = id;

            transaction = transactionDAO.GetById(id);
            if(transaction != null){
                category = categoryDAO.GetById(transaction.getCategoryId());
                wallet = walletDAO.GetById(transaction.getWalletId());

                //set cho radio group
                if(transaction.getAmount() < 0){
                    ((RadioButton) findViewById(R.id.chiRadio)).setChecked(true);
                    categorySpinner.setAdapter(categorySpinnerAdapterType0);
                }else{
                    ((RadioButton) findViewById(R.id.thuRadio)).setChecked(true);
                    categorySpinner.setAdapter(categorySpinnerAdapterType1);
                }

                //set du lieu cho spinner category
                List<Category> categories = ((CategorySpinnerAdapter)categorySpinner.getAdapter()).getCategories();
                for (Category c : categories) {
                    if(c.getId() == category.getId()){
                        categorySpinner.setSelection(categories.indexOf(c));
                    }
                }

                //set du lieu cho spinner wallet
                List<Wallet> walletList = walletSpinnerAdapter.getWallets();
                for (Wallet w : walletList) {
                    if(w.getId() == wallet.getId()){
                        walletSpinner.setSelection(walletList.indexOf(w));
                    }
                }

                amountInput.setText(NumberUltilities.FormatBalance( transaction.getAmount()), TextView.BufferType.EDITABLE);
                descriptionInput.setText(transaction.getDescription(), TextView.BufferType.EDITABLE);

                Date transactionDate = transaction.getDate();
                dateButton.setText(DateTimeUltilities.FormatDate("MMM dd yyyy", transactionDate));
                datePickerDialog.updateDate(transactionDate.getYear() + 1900, transactionDate.getMonth(), transactionDate.getDate());
            }
        }

        //exit button
        ImageView backImg = findViewById(R.id.exit_button);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //delete button
        ImageView deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transactionId != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransactionActivity.this);

                    builder.setMessage("Confirm delete this transaction?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Transaction transaction1 = transactionDAO.GetById(transactionId);
                                    if(transaction1 == null){
                                        Toast.makeText(getApplicationContext(), "Transaction not found for delete! Please check!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        transactionDAO.Delete(transaction1);
                                        Wallet wallet1 = walletDAO.GetById(transaction1.getWalletId());

                                        wallet1.setBalance(wallet1.getBalance() - transaction1.getAmount());
                                        globalWallet.setBalance(globalWallet.getBalance() - transaction1.getAmount());

                                        walletDAO.Update(wallet1);
                                        walletDAO.Update(globalWallet);

                                        finish();
                                    }
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog deleteConfirmDiaglog = builder.create();
                    deleteConfirmDiaglog.show();
                }
            }
        });

        //button save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView amountTV = findViewById(R.id.amount_input);
                TextView idTV = findViewById(R.id.transaction_id);
                TextView descriptionTV = findViewById(R.id.description_input);

                String transactionId_str = ((TextView)findViewById(R.id.transaction_id)).getText().toString();

                if(transactionId_str.length() > 0){
                    transaction = transactionDAO.GetById(Integer.parseInt(transactionId_str));
                }

                if(amountTV.getText() == null || amountTV.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    //TH add transaction
                    if(transaction == null){
                        transaction = new Transaction();
                        try{
                            transaction.setCategoryId(Integer.parseInt(((TextView)categorySpinner.findViewById(R.id.category_id)).getText().toString()));
                            transaction.setDate(DateTimeUltilities.StringToDate("MMM dd yyyy", dateButton.getText().toString()));
                            transaction.setWalletId(Integer.parseInt(((TextView)walletSpinner.findViewById(R.id.sp_wallet_id)).getText().toString()));
                            transaction.setDescription(descriptionTV.getText().toString());

                            Category category1 = categoryDAO.GetById(transaction.getCategoryId());
                            float amount = Float.parseFloat(amountTV.getText().toString());

                            if(amount <= 0) throw new Exception();

                            if(category1.getType() == 1){
                                transaction.setAmount(amount);
                            }else{
                                transaction.setAmount(-amount);
                            }

                            Wallet wallet1 = walletDAO.GetById(
                                    Integer.parseInt(
                                            ((TextView)findViewById(R.id.sp_wallet_id)).getText().toString()));

                            if(transaction.getAmount() <= wallet1.getBalance() ||
                                    categoryDAO.GetById(transaction.getCategoryId()).getType() == 1){
                                if(!transaction.getDate().after(new Date())){
                                    wallet1.setBalance(wallet1.getBalance() + transaction.getAmount());
                                    globalWallet.setBalance(globalWallet.getBalance() + transaction.getAmount());

                                    transactionDAO.Add(transaction);
                                    walletDAO.Update(wallet1);
                                    walletDAO.Update(globalWallet);

                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Không được nhập ngày trong tương lai!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Số tiền vượt quá số dư ví!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Vui lòng nhập sồ tiền hợp lệ!", Toast.LENGTH_SHORT).show();
                        }

                        //TH edit transaction
                    }else{
                        try {
                            Wallet wallet1 = walletDAO.GetById(
                                    Integer.parseInt(
                                            ((TextView)findViewById(R.id.sp_wallet_id)).getText().toString()));
                            wallet1.setBalance(wallet1.getBalance() - transaction.getAmount());
                            globalWallet.setBalance(globalWallet.getBalance() - transaction.getAmount());

                            transaction.setCategoryId(Integer.parseInt(((TextView)categorySpinner.findViewById(R.id.category_id)).getText().toString()));
                            transaction.setDate(DateTimeUltilities.StringToDate("MMM dd yyyy", dateButton.getText().toString()));
                            transaction.setWalletId(Integer.parseInt(((TextView)walletSpinner.findViewById(R.id.sp_wallet_id)).getText().toString()));
                            transaction.setDescription(descriptionTV.getText().toString());

                            Category category1 = categoryDAO.GetById(transaction.getCategoryId());
                            float amount = Float.parseFloat(amountTV.getText().toString());

                            if(amount <= 0) throw new Exception();

                            if(category1.getType() == 1){
                                transaction.setAmount(amount);
                            }else{
                                transaction.setAmount(-amount);
                            }

                            if(transaction.getAmount() <= wallet1.getBalance() ||
                                    categoryDAO.GetById(transaction.getCategoryId()).getType() == 1){
                                if(!transaction.getDate().after(new Date())){
                                    wallet1.setBalance(wallet1.getBalance() + transaction.getAmount());
                                    globalWallet.setBalance(globalWallet.getBalance() + transaction.getAmount());

                                    transactionDAO.Update(transaction);
                                    walletDAO.Update(wallet1);
                                    walletDAO.Update(globalWallet);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Không được nhập ngày trong tương lai!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Số tiền vượt quá số dư ví!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Vui lòng nhập sồ tiền hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    }
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