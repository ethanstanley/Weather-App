package com.example.android.weatherapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;




public class MainActivity extends AppCompatActivity {
    private Button mMovieButton, mBackButton, mLoadButton;
    private TextView mMovieTextView;
    private EditText mMovieEditText;
    private JsonObjectRequest jsonobjectrequest, jsonobjectrequest2;
    private String input, output, output2, sources, sor;
    private Boolean nothappen;
    private String url;
    private Integer count;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("EzFinder");
        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadButton = (Button)findViewById(R.id.load_button);
        mMovieButton = (Button)findViewById(R.id.movie_button);
        mMovieTextView = (TextView)findViewById(R.id.movie_text);
        mMovieEditText = (EditText)findViewById(R.id.movie_input);
        nothappen = false;
        output = "";
        queue = Volley.newRequestQueue(this);
        mBackButton = (Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TitleActivity.class);
                startActivity(i);
            }
        });
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                nothappen = false;
                input = mMovieEditText.getText().toString();
                url = "http://api-public.guidebox.com/v2/search?api_key=df5a93b6d86bee50a360869619a0599cf06030c4&type=movie&field=title&query=" + input;


                jsonobjectrequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    output = response.getJSONArray("results").getJSONObject(0).getString("id");
                                } catch (Exception e) {
                                    mMovieTextView.setText("This movie could not be found. Try again.");
                                    nothappen = true;

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mMovieTextView.setText("This movie could not be found. Try again.");
                    }
                });
                queue.add(jsonobjectrequest);
                System.out.print(queue);
            }
        });
        mMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nothappen == false && output != "") {
                    String url2 = "http://api-public.guidebox.com/v2/movies/" + output + "?api_key=df5a93b6d86bee50a360869619a0599cf06030c4";
                    jsonobjectrequest2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        output2 = response.getString("release_year");
                                        int y = 0;
                                        Set<String> sourceset = new HashSet<String>();
                                        sources = "This movie is available on ";
                                        for(int x = 0; x < response.getJSONArray("subscription_web_sources").length(); x++){
                                            sourceset.add(response.getJSONArray("subscription_web_sources").getJSONObject(x).getString("source").toString());
                                        }
                                        for(int x = 0; x < response.getJSONArray("purchase_web_sources").length(); x++) {
                                            sourceset.add(response.getJSONArray("purchase_web_sources").getJSONObject(x).getString("source").toString());
                                        }
                                        for(int x = 0; x < response.getJSONArray("purchase_android_sources").length(); x++) {
                                            sourceset.add(response.getJSONArray("purchase_android_sources").getJSONObject(x).getString("source").toString());
                                        }
                                        String temp = "";
                                        for (String str : sourceset)
                                        {
                                            temp += ", " + str;
                                        }
                                        sources += temp.substring(2);
                                        String star = "";
                                        String maincar = "";
                                        star = response.getJSONArray("cast").getJSONObject(0).getString("name");
                                        maincar =  response.getJSONArray("cast").getJSONObject(0).getString("character_name");
                                        System.out.println(sources);
                                        String date = "";
                                        date = response.getString("release_year");
                                        Intent i = new Intent(MainActivity.this, Display.class);
                                        i.putExtra("movie", input);
                                        i.putExtra("id", output);
                                        i.putExtra("sources", sources);
                                        i.putExtra("date", date);
                                        i.putExtra("star", star);
                                        i.putExtra("maincar", maincar);
                                        startActivity(i);
                                    }
                                    catch (Exception e) {
                                        mMovieTextView.setText(e.toString());
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mMovieTextView.setText(error.toString());
                        }
                    });
                    queue.add(jsonobjectrequest2);
                }
                else if (nothappen){
                    mMovieTextView.setText("This movie could not be found. Try again.");
                }
                else{
                    mMovieTextView.setText("Please load the movie first.");
                }


            }

        });


    }
}