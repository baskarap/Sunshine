package com.baskara.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String forecast = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView textView = (TextView) rootView.findViewById(R.id.text_detail);
            textView.setText(forecast);
        }
        return rootView;
    }
}