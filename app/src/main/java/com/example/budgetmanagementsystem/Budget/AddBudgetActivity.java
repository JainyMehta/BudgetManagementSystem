package com.example.budgetmanagementsystem.Budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementsystem.Activities.HomePageActivity;
import com.example.budgetmanagementsystem.Category.AddCategoryActivity;
import com.example.budgetmanagementsystem.Category.ViewCategoryActivity;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerBudget;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerCategory;
import com.example.budgetmanagementsystem.Models.Budget;
import com.example.budgetmanagementsystem.Models.Category;
import com.example.budgetmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AddBudgetActivity extends AppCompatActivity {

    Spinner spyear, spmonth, spCategory;
    DatabaseHandlerCategory dbCategory;
    DatabaseHandlerBudget dbBudget;
    List categoryIds;
    TextView tvbudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        spCategory=(Spinner)findViewById(R.id.spCategoryName);
        spmonth=(Spinner)findViewById(R.id.spMonth);
        spyear=(Spinner)findViewById(R.id.spYear);
        dbCategory=new DatabaseHandlerCategory(this);
        dbBudget=new DatabaseHandlerBudget(this);
        tvbudget=(TextView)findViewById(R.id.txtBudget);

        createYearSpinner();
        createCategorySpinner();
    }

    private void createCategorySpinner() {
        ArrayList<String> catName=new ArrayList<>();
        categoryIds = new ArrayList();
        List<Category> cat = dbCategory.getAllCategorys();
        int i=1;
        if(cat!=null) {
            for (Category temp : cat) {
                catName.add(temp.getName());
                categoryIds.add(temp.getId());
            }
        }
        else{
            catName.add("No Categories to show!!");
        }
        ArrayAdapter<String> catAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catName);
        spCategory.setAdapter(catAdapter);
        spCategory.setTag(categoryIds);
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

    public void createBudget(View view) {
        Boolean check=dbBudget.addBudget(new Budget(Integer.parseInt(categoryIds.get(spCategory.getSelectedItemPosition()).toString()),(spmonth.getSelectedItemPosition()+1),Integer.parseInt(spyear.getSelectedItem().toString()),Double.parseDouble(tvbudget.getText().toString()),0.0));
        if(check==true) {
            Toast.makeText(this, "Budget added successfully", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(AddBudgetActivity.this, HomePageActivity.class);
            startActivity(it);
        }
        else{
            Toast.makeText(this, "Budget not added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}