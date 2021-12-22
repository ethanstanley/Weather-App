package com.example.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TitleActivity extends AppCompatActivity {
    private TextView mTitle, mQuestion;
    private Button mShow, mMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("EzFinder");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mShow = (Button)findViewById(R.id.show_button);
        mMovie = (Button)findViewById(R.id.movie_button);
        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TitleActivity.this, ShowActivity.class);
                startActivity(i);
            }
        });
        mMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

}
