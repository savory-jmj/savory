package com.example.textscanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    //private JsoupRun mJsoupRun;
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
        //j = mJsoupRun.getmList().get(recipeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = view.findViewById(R.id.bandName);
        nameTextView.setText(j.get(0));

        TextView descriptionTextView = view.findViewById(R.id.bandDescription);
        descriptionTextView.setText(j.get(1));
        

        //Rinse and repeat on so on for the entire list

        return view;

    }
}