package com.baskara.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(ForecastFragment.IntentKey.INTENT_KEY_FORECAST)) {
            String forecast = intent.getStringExtra(ForecastFragment.IntentKey.INTENT_KEY_FORECAST);
            TextView textDetail = (TextView) rootView.findViewById(R.id.textDetail);
            textDetail.setText(forecast);
        }

        return rootView;
    }
}
