package com.example.mr1aihoroscope.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mr1aihoroscope.R;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private Button sendButton;
    private Spinner zodiacSpinner;
    private RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.resulText);
        genderGroup = findViewById(R.id.genderGroup);
        zodiacSpinner = findViewById(R.id.zodiacSpinner);
        sendButton = findViewById(R.id.sendButton);
    }
}