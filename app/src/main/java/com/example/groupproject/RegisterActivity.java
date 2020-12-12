package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText name, pass, confirm;
    Button Create_btn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.txtEmail);
        pass = (EditText) findViewById(R.id.txtPassword);
        confirm = (EditText) findViewById(R.id.txtConfirm);

        Create_btn = (Button) findViewById(R.id.Create_btn);
        Create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                String name_S = name.getText().toString();
                String pass_S = pass.getText().toString();
                String confirm_S = confirm.getText().toString();

                if (name_S.equals("") || pass_S.equals("") || confirm_S.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass_S.equals(confirm_S)) {
                        Boolean checkEmail = db.checkEmail(name_S);
                        if (checkEmail == true) {
                            Boolean insert = db.insert(name_S, pass_S);
                            if (insert == true) {
                                Toast.makeText(getApplicationContext(), "Account Registered!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Account already Exists!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }

    public void goToInfo(View view) {
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
    }

    public void goToHome(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}