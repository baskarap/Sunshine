package com.baskara.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailFragment detailFragment = new DetailFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.detail_container, detailFragment)
                .commit();
    }
}
