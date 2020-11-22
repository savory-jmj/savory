package com.example.textscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.sql.Array;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    public static final String KEY_RECIPE_ID = "recipeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            ArrayList<String> bandId = getIntent().getStringArrayListExtra(KEY_RECIPE_ID);
            fragment = DetailsFragment.newInstance(bandId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }
}