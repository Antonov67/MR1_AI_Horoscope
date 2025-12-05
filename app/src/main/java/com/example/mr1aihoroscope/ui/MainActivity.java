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
import com.example.mr1aihoroscope.network.Api;
import com.example.mr1aihoroscope.network.OpenRouterRequest;
import com.example.mr1aihoroscope.network.OpenRouterResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private Button sendButton;
    private Spinner zodiacSpinner;
    private RadioGroup genderGroup;
    private Api api;

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

        Gson gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openrouter.ai/api/v1")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(Api.class);

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

                resultText.setText("\uD83D\uDCAD Думаю над подарком...");

                sendRequestToAI(gender, zodiac);
            }
        });

    }

    private void sendRequestToAI(String gender, String zodiac) {
        List<OpenRouterRequest.Message> messages = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, EEEE", new Locale("ru"));
        String formattedDate = sdf.format(date);

        String prompt = "Создай гороскоп для " + gender + " по знаку зодиака " + zodiac
                + " на " + formattedDate + ", ответь кратко и с юмором";
        messages.add(new OpenRouterRequest.Message(prompt));

        OpenRouterRequest request = new OpenRouterRequest(messages);

        api.sendMessage(request).enqueue(new Callback<OpenRouterResponse>() {
            @Override
            public void onResponse(Call<OpenRouterResponse> call, Response<OpenRouterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String reply = response.body().choices.get(0).message.content;

                        reply = reply.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");

                        // Отображаем HTML
                        resultText.setText(android.text.Html.fromHtml(
                                "🎁 Идея подарка:<br><br>" + reply,
                                android.text.Html.FROM_HTML_MODE_LEGACY
                        ));
                    } catch (Exception e) {
                        resultText.setText("Ошибка разбора ответа: " + e.getMessage());
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "empty";
                        resultText.setText("Ошибка: " + response.code() + "\n" + errorBody);
                    } catch (Exception e) {
                        resultText.setText("Ошибка: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<OpenRouterResponse> call, Throwable t) {
                resultText.setText("Ошибка сети: " + t.getMessage());
            }
        });
    }

    private void initZodiacSpinner() {
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