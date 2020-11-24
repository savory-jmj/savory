package com.example.textscanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    private JsoupRun mJsoupRun;
    private List<String> j;

    public static DetailsFragment newInstance(ArrayList<String> recipeId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("recipeId", recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        j = getArguments().getStringArrayList("recipeId");
        int recipeId = 1;
        if(getArguments() != null){
            recipeId = getArguments().getInt("recipeId");
        }
        else {
            j = mJsoupRun.getmList().get(recipeId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView titleTextView = view.findViewById(R.id.title);
        titleTextView.setText(j.get(0));

        TextView summaryTextView = view.findViewById(R.id.summary);
        summaryTextView.setText(j.get(1));

        TextView infoTextView = view.findViewById(R.id.info);
        infoTextView.setText(j.get(2));

        TextView ingredientsTextView = view.findViewById(R.id.ingredients);
        ingredientsTextView.setText(j.get(3));

        TextView directionsTextView = view.findViewById(R.id.directions);
        directionsTextView.setText(j.get(4));

        ImageView foodImageView = view.findViewById(R.id.foodImage);
        Picasso.get().load(j.get(5)).into(foodImageView);

        return view;

    }
}