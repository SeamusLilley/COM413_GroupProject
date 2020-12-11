package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    private Button LogIn;
    private Button SignUp;
    private ImageButton Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Info = (ImageButton) findViewById(R.id.info);
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info();
            }
        });
        SignUp = (Button) findViewById(R.id.signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        LogIn = (Button) findViewById(R.id.login);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void info() {
        Intent intent = new Intent (this, InfoActivity.class);
        startActivity(intent);
    }
    public void signup() {
        Intent intent = new Intent (this, MainActivity.class); // change to SignUpActivity
        startActivity(intent);
    }
    public void login() {
        Intent intent = new Intent(this, MainActivity.class); // change to LoginActivity
        startActivity(intent);
    }
}