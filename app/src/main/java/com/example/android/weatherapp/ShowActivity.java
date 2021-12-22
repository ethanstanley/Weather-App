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




public class ShowActivity extends AppCompatActivity {
    private Button mShowButton, mBackButton, mLoadButton2;
    private TextView mShowTextView;
    private EditText mShowEditText;
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
        setContentView(R.layout.activity_show);
        mShowButton = (Button) findViewById(R.id.show_button);
        mLoadButton2 = (Button) findViewById(R.id.load_button2);
        mShowTextView = (TextView) findViewById(R.id.show_text);
        mShowEditText = (EditText) findViewById(R.id.show_input);
        output = "";
        nothappen = false;
        queue = Volley.newRequestQueue(this);
        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowActivity.this, TitleActivity.class);
                startActivity(i);
            }
        });
        mLoadButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                nothappen = false;
                input = mShowEditText.getText().toString();
                url = "http://api-public.guidebox.com/v2/search?api_key=df5a93b6d86bee50a360869619a0599cf06030c4&type=show&field=title&query=" + input;


                jsonobjectrequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    output = response.getJSONArray("results").getJSONObject(0).getString("id");
                                } catch (Exception e) {
                                    mShowTextView.setText("This show could not be found. Try again.");
                                    nothappen = true;

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        nothappen = true;
                        mShowTextView.setText("This show could not be found. Try again.");
                    }
                });
                queue.add(jsonobjectrequest);
                System.out.print(queue);
                System.out.print(output);
            }
        });

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nothappen == false && output != "") {
                    String url2 = "http://api-public.guidebox.com/v2/shows/" + output + "?api_key=df5a93b6d86bee50a360869619a0599cf06030c4";
                    jsonobjectrequest2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //output2 = response.getString("first_aired");
                                        int y = 0;
                                        Set<String> sourceset = new HashSet<String>();
                                        sources = "This show is available on ";
                                        for (int x = 0; x < response.getJSONArray("channels").length(); x++) {
                                            sourceset.add(response.getJSONArray("channels").getJSONObject(x).getString("name").toString());
                                        }
                                        String temp = "";
                                        for (String str : sourceset) {
                                            temp += ", " + str;
                                        }
                                        sources += temp.substring(2);
                                        String star = "";
                                        String maincar = "";
                                        star = response.getJSONArray("cast").getJSONObject(0).getString("name");
                                        maincar = response.getJSONArray("cast").getJSONObject(0).getString("character_name");
                                        System.out.println(sources);
                                        String date = "";
                                        date = response.getString("first_aired").substring(0, 4);
                                        System.out.print(date);
                                        String genre = "";
                                        genre = response.getJSONArray("genres").getJSONObject(0).getString("title");
                                        Intent i = new Intent(ShowActivity.this, DisplayShow.class);
                                        System.out.println(input);
                                        i.putExtra("show", input);
                                        System.out.println(output);
                                        i.putExtra("genre", genre);
                                        i.putExtra("id", output);
                                        i.putExtra("date", date);
                                        System.out.println(sources);
                                        i.putExtra("sources", sources);
                                        System.out.println(star);
                                        i.putExtra("star", star);
                                        System.out.println(maincar);
                                        i.putExtra("maincar", maincar);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        mShowTextView.setText("This show could not be found. Try again.");
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mShowTextView.setText("This show could not be found. Try again.");
                        }
                    });
                    queue.add(jsonobjectrequest2);
                }
                else if (nothappen){
                    mShowTextView.setText("This show could not be found. Try again.");
                }
                else{
                    mShowTextView.setText("Please load show.");
                }
            }
        });


        }

}
