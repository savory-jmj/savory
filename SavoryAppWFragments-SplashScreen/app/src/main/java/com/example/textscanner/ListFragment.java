package com.example.textscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.textscanner.ModifiedwebScraper.src.GoogleSheetsService;
import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private JsoupRun j;
    private GoogleSheetsService g;


    // For the activity to implement
    public interface OnRecipeSelectedListener {
        void onRecipeSelected(ArrayList<String> recipeId);
    }

    // Reference to the activity
    private OnRecipeSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        j = new JsoupRun();
        g = new GoogleSheetsService();

        Bundle bundle = this.getArguments();

        if (bundle != null){
            try {
                if (bundle.get("Link").toString().equals("")) {
                    System.out.println("in here");
                    j.setmList(g.getRecipes(0));
                } else {
                    System.out.println("in jsoup run");
                    j.run(bundle.get("Link").toString());
                }
            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        }

        // Send recipe to recycler view
        RecipeAdapter adapter = new RecipeAdapter(j.getmList());
        recyclerView.setAdapter(adapter);

        return view;

    }

    private class RecipeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mNameTextView;
        private ImageView mFoodImageView;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.recipeName);
            mFoodImageView = (ImageView) itemView.findViewById(R.id.foodImageView);
        }

        public void bind(List<String> recipe) {
            List<String> r = recipe;
            mNameTextView.setText(r.get(0));
            if (r.get(5).equals(null)) {
                mFoodImageView.setImageDrawable(ContextCompat.getDrawable(j.getApplicationContext(), R.drawable.savory));
            } else {
                System.out.println("image: " + r.get(5));
                Picasso.get().load(r.get(5)).into(mFoodImageView);
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onRecipeSelected(j.search(mNameTextView.getText().toString()));
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {

        private List<ArrayList<String>> mJsoupRun;

        public RecipeAdapter(List<ArrayList<String>> recipes) {
            mJsoupRun = recipes;
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            List<String> recipe = mJsoupRun.get(position);
            holder.bind(recipe);
        }

        @Override
        public int getItemCount() {
            return mJsoupRun.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeSelectedListener) {
            mListener = (OnRecipeSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}