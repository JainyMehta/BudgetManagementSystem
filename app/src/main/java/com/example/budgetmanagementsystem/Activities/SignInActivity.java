package com.example.budgetmanagementsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementsystem.DatabaseHandlers.DatabaseHandlerUser;
import com.example.budgetmanagementsystem.Models.User;
import com.example.budgetmanagementsystem.R;

public class SignInActivity extends AppCompatActivity {

    ImageView sback;
    TextView txtUsername, txtPassword, txtSignIn;
    DatabaseHandlerUser dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sback = (ImageView) findViewById(R.id.sinb);
        dbUser = new DatabaseHandlerUser(this);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        txtUsername = (TextView) findViewById(R.id.usrusr);
        txtPassword = (TextView) findViewById(R.id.pswrd);
        txtSignIn = (TextView) findViewById(R.id.sin);
    }

    public void signIn(View view) {
        User user = dbUser.signIn(txtUsername.getText().toString(), txtPassword.getText().toString());
        if (user == null) {
            Toast.makeText(SignInActivity.this, "Invalid!!Try again!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(SignInActivity.this, "Welcome, " + user.getName() + "!!", Toast.LENGTH_LONG).show();
            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("BudgetManagementSystemPref", MODE_PRIVATE);

            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            // Storing the key and its value as the data fetched from edittext
            myEdit.putInt("id", user.getId());
            myEdit.putString("name", user.getName());
            myEdit.putString("email",user.getEmail());
            myEdit.putString("username",user.getUsername());

            // Once the changes have been made,
            // we need to commit to apply those changes made,
            // otherwise, it will throw an error
            myEdit.commit();
            Intent it = new Intent(SignInActivity.this, HomePageActivity.class);
            startActivity(it);

        }
    }
}