package com.example.budgetmanagementsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.budgetmanagementsystem.Budget.AddBudgetActivity;
import com.example.budgetmanagementsystem.Category.ViewCategoryActivity;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerBudget;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerCategory;
import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerTransaction;
import com.example.budgetmanagementsystem.Models.Budget;
import com.example.budgetmanagementsystem.Models.BudgetProgress;
import com.example.budgetmanagementsystem.Models.Category;
import com.example.budgetmanagementsystem.R;
import com.example.budgetmanagementsystem.Transaction.MonthPickerViewTransactionActivity;
import com.example.budgetmanagementsystem.Transaction.TransactionDateActivity;
import com.example.budgetmanagementsystem.Transaction.ViewTransactionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    DatabaseHandlerBudget dbBudget;
    DatabaseHandlerCategory dbCategory;
    DatabaseHandlerTransaction dbTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbBudget=new DatabaseHandlerBudget(this);
        dbCategory=new DatabaseHandlerCategory(this);
        dbTransaction=new DatabaseHandlerTransaction(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomePageActivity.this, TransactionDateActivity.class);
                startActivity(it);
            }
        });

        setListBudget();
    }

    private void setListBudget() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        List<Budget> catId=dbBudget.getAllCategoriesMonthlyofBudgets(month+1, year);
        List<BudgetProgress> budProgress=new ArrayList<>();
        Category cg=new Category();

        for(Budget id:catId){
            cg=dbCategory.getCategory(id.getCategoryId());
            budProgress.add(new BudgetProgress(cg.getName(), (int) id.getBudget(),dbTransaction.getSumMonthTransactions(month+1,year,id.getCategoryId())));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        MyListAdapter adapter = new MyListAdapter(budProgress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_category:
                Intent it1 = new Intent(HomePageActivity.this, ViewCategoryActivity.class);
                startActivity(it1);
                break;
            case R.id.nav_budget:
                Intent it2 = new Intent(HomePageActivity.this, AddBudgetActivity.class);
                startActivity(it2);
                break;
            case R.id.nav_csv:
                Intent it3 = new Intent(HomePageActivity.this, MonthPickerViewTransactionActivity.class);
                startActivity(it3);
                break;
            case R.id.nav_logout:
                SharedPreferences settings = getApplicationContext().getSharedPreferences("BudgetManagementSystemPref", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent it = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(it);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}