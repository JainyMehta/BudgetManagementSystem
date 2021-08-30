package com.example.budgetmanagementsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerUser;
import com.example.budgetmanagementsystem.Models.User;
import com.example.budgetmanagementsystem.R;

import org.w3c.dom.Text;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    ImageView sback;
    TextView btnCreate, fname, email, username, password;
    DatabaseHandlerUser dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sback = (ImageView)findViewById(R.id.sback);        //back arrow button
        btnCreate=(TextView)findViewById(R.id.sin);
        fname=(TextView)findViewById(R.id.fname);
        email=(TextView)findViewById(R.id.mail);
        username=(TextView)findViewById(R.id.usrusr);
        password=(TextView)findViewById(R.id.pswrd);
        dbUser=new DatabaseHandlerUser(this);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
//        List<User> user = dbUser.getAllUsers();
//
//        for (User cn : user) {
//            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() + " ,Username: " +
//                    cn.getUsername()+" ,Password"+cn.getPassword();
//
//            // Writing Contacts to log
//            Log.d("Name:", log+"=========================================================");
//        }

    }

    public void createAccount(View view) {
        Boolean check=dbUser.addUser(new User(fname.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString()));
        if(check == true){
            Toast.makeText(SignUpActivity.this,"Registration was successful!!",Toast.LENGTH_LONG).show();
            Intent it = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(it);
        }
        else{
            Toast.makeText(SignUpActivity.this,"Registration was not successful!!Try again!",Toast.LENGTH_LONG).show();

        }
    }
}