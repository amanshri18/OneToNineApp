package com.retailer.user11.onetonineapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.adapter.KickHistoryAdapter;
import com.retailer.user11.onetonineapp.model.KickItem;
import com.retailer.user11.onetonineapp.utils.ApiLink;
import com.retailer.user11.onetonineapp.utils.CheckInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KickDetailsActivity extends AppCompatActivity {

    protected RecyclerView rvKick;
    protected ProgressBar progress;
    KickHistoryAdapter kickHistoryAdapter;
    ArrayList<KickItem> kickItems;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_kick_details);
        initView();
        kickItems = new ArrayList<>();
        initRecyclerView();
        user_id = getIntent().getStringExtra("user_id");
        if (CheckInternet.isConnectingToInternet(this)) {
            getData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rvKick = findViewById(R.id.rv_kick);
        progress = findViewById(R.id.progress);
    }

    public void initRecyclerView() {
        rvKick.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvKick.setLayoutManager(mLayoutManager);

    }

    private void getData() {
        progress.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiLink.URL + "kick_get.php?user_id=" + user_id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        KickItem kickItem = new KickItem();
                        kickItem.setDate(jsonObject.getString("k_date"));
                        kickItem.setStart(jsonObject.getString("start"));
                        kickItem.setDuration(jsonObject.getString("duration"));
                        kickItem.setKicks(jsonObject.getString("kicks"));
                        kickItems.add(kickItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                    }
                }
                kickHistoryAdapter = new KickHistoryAdapter(KickDetailsActivity.this, kickItems);
                rvKick.setAdapter(kickHistoryAdapter);
                progress.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley", volleyError.toString());
                progress.setVisibility(View.GONE);
                String message = null;

                if (volleyError instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!",
                            Toast.LENGTH_LONG).show();

                } else if (volleyError instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!",
                            Toast.LENGTH_LONG).show();

                } else if (volleyError instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Database server...Please check your connection!",
                            Toast.LENGTH_LONG).show();

                }  else if (volleyError instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!",
                            Toast.LENGTH_LONG).show();

                } else if (volleyError instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
