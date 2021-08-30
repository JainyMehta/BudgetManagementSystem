package com.example.budgetmanagementsystem.Transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementsystem.Activities.HomePageActivity;
import com.example.budgetmanagementsystem.Budget.AddBudgetActivity;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerBudget;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerCategory;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerTransaction;
import com.example.budgetmanagementsystem.Models.Category;
import com.example.budgetmanagementsystem.Models.Transaction;
import com.example.budgetmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    int date, month, year;
    DatabaseHandlerBudget dbBudget;
    DatabaseHandlerCategory dbCategory;
    DatabaseHandlerTransaction dbTransaction;
    List<Integer> catId;
    List<String> catName;
    Spinner spCategory;
    TextView txtName, txtDescription, txtAmount;
    CheckBox cbIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Intent i=getIntent();
        date=i.getIntExtra("Date",0);
        month=i.getIntExtra("Month",0);
        year=i.getIntExtra("Year",0);
        System.out.println("============="+year+"========"+month+"============"+date);
        dbBudget=new DatabaseHandlerBudget(this);
        dbCategory=new DatabaseHandlerCategory(this);
        dbTransaction=new DatabaseHandlerTransaction(this);
        Category cg=new Category();
        spCategory=(Spinner)findViewById(R.id.spCategory);
        txtName=(TextView)findViewById(R.id.txtName);
        txtDescription=(TextView)findViewById(R.id.txtDescription);
        txtAmount=(TextView)findViewById(R.id.txtAmount);
        cbIncome=(CheckBox)findViewById(R.id.cbIncome);

        catId=dbBudget.getAllCategoriesofBudgets(month, year);

        catName=new ArrayList<String>();
        for(Integer id:catId){
            cg=dbCategory.getCategory(id);
            System.out.println("==================="+id+"========="+cg.getName());
            catName.add(cg.getName());
        }

        ArrayAdapter<String> catAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catName);
        spCategory.setAdapter(catAdapter);
        spCategory.setTag(catId);
    }

    public void createBudget(View view) {
        int cbCheck=0;
        if(cbIncome.isChecked()){
            cbCheck=1;
        }
        Boolean check=dbTransaction.addTransaction(new Transaction(txtName.getText().toString(),cbCheck,date,month,year,Double.parseDouble(txtAmount.getText().toString()),
                txtDescription.getText().toString(),Integer.parseInt(catId.get(spCategory.getSelectedItemPosition()).toString())));

        if(check==true) {
            Toast.makeText(this, "Transaction added successfully", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(AddTransactionActivity.this, HomePageActivity.class);
            startActivity(it);
        }
        else{
            Toast.makeText(this, "Transaction not added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}