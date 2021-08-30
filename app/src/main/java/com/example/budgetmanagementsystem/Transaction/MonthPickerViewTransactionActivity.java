package com.example.budgetmanagementsystem.Transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.budgetmanagementsystem.R;

import java.util.ArrayList;

public class MonthPickerViewTransactionActivity extends AppCompatActivity {

    Spinner spyear, spmonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_picker_view_transaction);

        spmonth=(Spinner)findViewById(R.id.spMonth);
        spyear=(Spinner)findViewById(R.id.spYear);
        createYearSpinner();
    }

    public void createYearSpinner(){
        ArrayList<String> years=new ArrayList<>();
        int minYear=2015;
        for(int i=minYear; i<=2021; i++){
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years);
        spyear.setAdapter(yearAdapter);
    }

    public void getTransactions(View view) {
        Intent it=new Intent(MonthPickerViewTransactionActivity.this, ViewTransactionActivity.class);
        it.putExtra("Month",spmonth.getSelectedItemPosition()+1);
        it.putExtra("Year",Integer.parseInt(spyear.getSelectedItem().toString()));
        startActivity(it);
    }
}