package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        // set the title box to the marker's title
        TextView txt_info = findViewById(R.id.txt_info);
        String title = getIntent().getStringExtra("title");
        txt_info.setText(title);

        // set the description box to the marker's description
        TextView txt_description = findViewById(R.id.txt_description);
        String description = getIntent().getStringExtra("information");
        txt_description.setText(description);
    }
}