package com.example.bhavanitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Model> movieList;
    RequestQueue requestQueue;
    String JsonURL = "http://13.52.148.6:7994/rc1/get_up_coming_movies?country_code=IN&languages=Bengali,English,Hindi,Kannada,Malayalam,Tamil,Telugu&player_id=5d836d53fcd9b107ce145c5b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (RecyclerView) findViewById(R.id.main_list);


//        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
 //       mList.setHasFixedSize(true);
 //       mList.setLayoutManager(new LinearLayoutManager(this));




        mList.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mList.setLayoutManager(horizontalLayoutManager);
        getData();


    }

    private void getData() {
        try {


            String url = "http://13.52.148.6:7994/rc1/get_up_coming_movies?country_code=IN&languages=Bengali,English,Hindi,Kannada,Malayalam,Tamil,Telugu&player_id=5d836d53fcd9b107ce145c5b";

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Initialize a new JsonObjectRequest instance
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Do something with response
                                //mTextView.setText(response.toString());
                                Log.e("response", String.valueOf(response));
                                movieList = new ArrayList<Model>();

                                // Process the JSON
                                try {
                                    // Get the JSON array
                                    JSONArray array = response.getJSONArray("data");

                                    // Loop through the array elements
                                    for (int i = 0; i < array.length(); i++) {
                                        // Get current json object
                                        Model m = new Model();
                                        JSONObject data = array.getJSONObject(i);
                                        m.setMovie_thumbnail_image(data.getString("movie_thumbnail_image"));
                                        m.setMovie_title(data.getString("movie_title"));
                                        movieList.add(m);
                                    }
                                    MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieList);
                                    mList.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do something when error occurred

                            }
                        }
                );
                requestQueue.add(jsonObjectRequest);

            } catch (Exception e) {
                Log.e("TAG", "getData: " + e);
            }


            // Add JsonObjectRequest to the RequestQueue


        } catch (Exception e) {
            Log.e("TAG", "getData: " + e);
        }
    }


}
