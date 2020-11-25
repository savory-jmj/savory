package com.example.textscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void receipt(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void browse(View view) {
        EditText edit = findViewById(R.id.editTextId);
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        intent.putExtra("Link",edit.getText().toString());
        startActivity(intent);
    }
}