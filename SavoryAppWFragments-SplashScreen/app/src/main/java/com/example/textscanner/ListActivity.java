package com.example.textscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ListFragment.OnRecipeSelectedListener {

    private static final String KEY_RECIPE_ID = "recipeId";
    private ArrayList<String> mRecipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String text = getIntent().getStringExtra("Link");

        mRecipeId = new ArrayList<>();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        Bundle bundle = new Bundle();
        bundle.putString("Link",text);

        if (fragment == null) {
            fragment = new ListFragment();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }
    @Override
    public void onRecipeSelected(ArrayList<String> recipeId) {

        mRecipeId = recipeId;

        if (findViewById(R.id.details_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putStringArrayListExtra(DetailsActivity.KEY_RECIPE_ID, mRecipeId);
            startActivity(intent);
        } else {
            // Replace previous fragment (if one exists) with a new fragment
            Fragment recipeFragment = DetailsFragment.newInstance(mRecipeId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, recipeFragment)
                    .commit();
        }
    }

}