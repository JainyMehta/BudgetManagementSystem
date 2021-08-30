package com.example.budgetmanagementsystem.Transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.budgetmanagementsystem.Activities.HomePageActivity;
import com.example.budgetmanagementsystem.Models.Transaction;
import com.example.budgetmanagementsystem.R;

public class TransactionDateActivity extends AppCompatActivity {

    DatePicker picker;
    Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_date);

        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnGet=(Button)findViewById(R.id.btn_proceed);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TransactionDateActivity.this, AddTransactionActivity.class);
                it.putExtra("Date",picker.getDayOfMonth());
                it.putExtra("Month",(picker.getMonth() + 1));
                it.putExtra("Year",(picker.getYear()));
                startActivity(it);
            }
        });

    }
}