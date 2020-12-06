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

    // receipt() is called when Scan Receipt button is clicked. It changes the view to the MainActivity.
    public void receipt(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    // browse() is called when Browse button is clicked. 
    // It changes the view to the ListActivity and passes an empty string so that recipes from the database is displayed.
    public void browse(View view) {
        EditText edit = findViewById(R.id.editTextId);
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        intent.putExtra("Link",edit.getText().toString());
        startActivity(intent);
    }
}
