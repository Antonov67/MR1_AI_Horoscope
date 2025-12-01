package com.example.mr1aihoroscope.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        initZodiacSpinner();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = genderGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getApplicationContext(), "Выберите пол", Toast.LENGTH_SHORT).show();
                    return;
                }
                String gender = ((RadioButton) findViewById(selectedId)).getText().toString();
                String zodiac = zodiacSpinner.getSelectedItem().toString();

                resultText.setText("💭 Думаю над подарком...");

                sendRequestToAI(gender, zodiac);
            }
        });

    }

    private void sendRequestToAI(String gender, String zodiac) {
    }

    private void initZodiacSpinner(){
        String[] zodiacSigns = {
                "Овен", "Телец", "Близнецы", "Рак",
                "Лев", "Дева", "Весы", "Скорпион",
                "Стрелец", "Козерог", "Водолей", "Рыбы"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                zodiacSigns
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zodiacSpinner.setAdapter(adapter);
    }
}