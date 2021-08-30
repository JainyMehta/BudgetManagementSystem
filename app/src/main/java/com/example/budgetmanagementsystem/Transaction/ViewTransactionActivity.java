package com.example.budgetmanagementsystem.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerTransaction;
import com.example.budgetmanagementsystem.Models.Transaction;
import com.example.budgetmanagementsystem.R;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {

    private Sheet sheet = null;
    private static String EXCEL_SHEET_NAME = "Sheet1";
    private Cell cell = null;
    int month, year;
    DatabaseHandlerTransaction dbTransaction;
    private File filePath = new File(   Environment.getExternalStorageDirectory() + "/Documents/Demo.xls");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
        Intent i=getIntent();
        month=i.getIntExtra("Month",0);
        year=i.getIntExtra("Year",0);
        System.out.println("=========================="+month+year+"=================================="+Environment.getExternalStorageDirectory());
        dbTransaction=new DatabaseHandlerTransaction(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createTransactionWorkbook();
    }

    private void createTransactionWorkbook() {

        Workbook workbook = new HSSFWorkbook();

        cell = null;

        // Cell style for header row
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        // New Sheet
        sheet = null;
        sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        // Generate column headings
        Row row = sheet.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("Name");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Description");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Date");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Amount");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("Is Income?");
        cell.setCellStyle(cellStyle);

        cellStyle.setFillForegroundColor(HSSFColor.TEAL.index);
        List<Transaction> monthlyTransaction=dbTransaction.getAllMonthTransactions(month,year);

        int cnt=1;
        int rowCell=0;

        for(Transaction  id:monthlyTransaction){
            row = sheet.createRow(cnt);

            cell = row.createCell(rowCell);
            cell.setCellValue(id.getName());
            cell.setCellStyle(cellStyle);
            rowCell++;

            cell = row.createCell(rowCell);
            cell.setCellValue(id.getDescription());
            cell.setCellStyle(cellStyle);
            rowCell++;

            cell = row.createCell(rowCell);
            cell.setCellValue(id.getDate()+"/"+id.getMonth()+"/"+id.getYear());
            cell.setCellStyle(cellStyle);
            rowCell++;

            cell = row.createCell(rowCell);
            cell.setCellValue(id.getPrice());
            cell.setCellStyle(cellStyle);
            rowCell++;

            cell = row.createCell(rowCell);
            if(id.getIsIncome()==0) {
                cell.setCellValue("No");
            }else{
                cell.setCellValue("Yes");
            }
            cell.setCellStyle(cellStyle);

            rowCell=0;
            cnt++;

            try {
                if (!filePath.exists()){
                    filePath.createNewFile();
                }

                FileOutputStream fileOutputStream= new FileOutputStream(filePath);
                workbook.write(fileOutputStream);
                Toast.makeText(getApplicationContext(), "Excel file created", Toast.LENGTH_SHORT).show();

                if (fileOutputStream!=null){
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}