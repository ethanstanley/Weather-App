package com.example.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Display extends AppCompatActivity {
    private TextView mMovieSources, mMovieName ;
    private String output, output2;
    private Boolean nothappen;
    private String url, movie, id, sources, input, maincar, star, date;
    RequestQueue queue;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("EzFinder");
        movie = getIntent().getStringExtra("movie");
        id = getIntent().getStringExtra("id");
        sources = getIntent().getStringExtra("sources").replace("_", " ");
        input = getIntent().getStringExtra("maincar");
        star = getIntent().getStringExtra("star");
        date = getIntent().getStringExtra("date");
        mMovieName = (TextView)findViewById(R.id.movie_name);
        mMovieName.setText(movie + "\n");
        mMovieSources = (TextView)findViewById(R.id.movie_sources);
        nothappen = false;
        if(input.length() < 2)
            mMovieSources.setText(sources + ", and was released in " + date + ". It stars " + star + '.');
        else
            mMovieSources.setText(sources + ", and was released in " + date + ". It stars " + star + " as " + input + ".\n");
        mBackButton = (Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Display.this, MainActivity.class);
                startActivity(i);
            }
        });
        // Instantiate the RequestQueue.
// Request a string response from the provided UR

    }

}
