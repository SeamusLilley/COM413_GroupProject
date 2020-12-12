package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText txt_EmailNew, txtPasswordNew;
    Button Login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        txt_EmailNew = (EditText) findViewById(R.id.txt_EmailNew);
        txtPasswordNew = (EditText) findViewById(R.id.txt_PasswordNew);


        Login_btn = (Button) findViewById(R.id.Login_btn);
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = txt_EmailNew.getText().toString();
                String Password = txtPasswordNew.getText().toString();

                if (Username.equals("") || Password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkEmail = db.checkEmail(Username);
                    if (checkEmail == false) {
                        Boolean checkPassword = db.checkPassword(Username, Password);
                        if (checkPassword == true) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Username or Password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Username or Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void goToInfo(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void goToMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}