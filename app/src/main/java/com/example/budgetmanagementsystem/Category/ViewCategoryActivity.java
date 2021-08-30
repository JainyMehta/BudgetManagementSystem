package com.example.budgetmanagementsystem.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.budgetmanagementsystem.Activities.MainActivity;
import com.example.budgetmanagementsystem.Activities.SignUpActivity;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerCategory;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerUser;
import com.example.budgetmanagementsystem.Models.Category;
import com.example.budgetmanagementsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    CategoryRecyclerViewAdapter adapter;
    DatabaseHandlerCategory dbCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);
        dbCategory=new DatabaseHandlerCategory(this);
        ArrayList<String> catName=new ArrayList<>();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ViewCategoryActivity.this, AddCategoryActivity.class);
                startActivity(it);
            }
        });

            List<Category> cat = dbCategory.getAllCategorys();
            if(cat!=null) {
                for (Category temp : cat) {
                    catName.add(temp.getName());
                }
            }
            else{
                catName.add("No Categories to show!!");
            }



        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new CategoryRecyclerViewAdapter(this, catName);

        recyclerView.setAdapter(adapter);
    }
}