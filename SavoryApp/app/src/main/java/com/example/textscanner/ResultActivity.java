package com.example.textscanner;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private JsoupRun j;
    private TextView textViewTitle;
    private TextView textViewSummary;
    private TextView textViewInfo;
    private TextView textViewIngredient;
    private TextView textViewDirection;
    private String imageUrl;
    private ImageView imageViewFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String link = getIntent().getStringExtra("Link");
        List<String> infoList = new ArrayList<>();
        j = new JsoupRun();
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewSummary = findViewById(R.id.textViewSummary);
        textViewInfo = findViewById(R.id.textViewInfo);
        textViewIngredient = findViewById(R.id.textViewIngredient);
        textViewDirection = findViewById(R.id.textViewDirection);
        imageViewFood = findViewById(R.id.imageViewFood);


        try {
            infoList = j.getInfo(link);
            textViewTitle.setText(infoList.get(0));
            textViewSummary.setText(infoList.get(1));
            textViewInfo.setText(infoList.get(2));
            textViewIngredient.setText(infoList.get(3));
            textViewDirection.setText(infoList.get(4));
            imageUrl = infoList.get(5);
            Picasso.get().load(imageUrl).into(imageViewFood);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
