package com.example.textscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.textscanner.ModifiedwebScraper.src.JsoupRun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private JsoupRun j;
    // For the activity to implement
    public interface OnBandSelectedListener {
        void onBandSelected(ArrayList<String> recipeId);
    }

    // Reference to the activity
    private OnBandSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.band_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        j = new JsoupRun();

        Bundle bundle = this.getArguments();

        if (bundle != null){
            try {
                j.run(bundle.get("Link").toString());
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else{
            //j.setmList(google database list); <----------------------------------------- HERE
        }

        // Send bands to recycler view
        BandAdapter adapter = new BandAdapter(j.getmList());
        recyclerView.setAdapter(adapter);

        return view;

    }

    private class BandHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mNameTextView;

        public BandHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.bandName);
        }

        public void bind(List<String> recipe) {
            List<String> r = recipe;
            mNameTextView.setText(r.get(0));
        }

        @Override
        public void onClick(View view) {
            mListener.onBandSelected(j.search(mNameTextView.getText().toString()));
        }
    }

    private class BandAdapter extends RecyclerView.Adapter<BandHolder> {

        private List<ArrayList<String>> mJsoupRun;

        public BandAdapter(List<ArrayList<String>> bands) {
            mJsoupRun = bands;
        }

        @Override
        public BandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BandHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BandHolder holder, int position) {
            List<String> band = mJsoupRun.get(position);
            holder.bind(band);
        }

        @Override
        public int getItemCount() {
            return mJsoupRun.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBandSelectedListener) {
            mListener = (OnBandSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBandSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}