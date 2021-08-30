package com.example.budgetmanagementsystem.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerCategory;
import com.example.budgetmanagementsystem.Models.Category;
import com.example.budgetmanagementsystem.R;

public class AddCategoryActivity extends AppCompatActivity {

    DatabaseHandlerCategory dbCategory;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        dbCategory=new DatabaseHandlerCategory(this);
        tv=(TextView)findViewById(R.id.tvcategory);
    }

    public void addCategory(View view) {
        Boolean check=dbCategory.addCategory(new Category(tv.getText().toString()));
        if(check==true) {
            Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(AddCategoryActivity.this, ViewCategoryActivity.class);
            startActivity(it);
        }
        else{
            Toast.makeText(this, "Category not added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}