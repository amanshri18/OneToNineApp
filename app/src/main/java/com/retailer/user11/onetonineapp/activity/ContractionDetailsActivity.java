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
import com.retailer.user11.onetonineapp.adapter.ContractionAdapter;
import com.retailer.user11.onetonineapp.model.LapItem;
import com.retailer.user11.onetonineapp.utils.ApiLink;
import com.retailer.user11.onetonineapp.utils.CheckInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContractionDetailsActivity extends AppCompatActivity {

    protected RecyclerView rvContraction;
    protected ProgressBar progress;
    ContractionAdapter contractionAdapter;
    List<LapItem> lapItems;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_contraction_details);
        initView();
        lapItems = new ArrayList<>();
        user_id = getIntent().getStringExtra("user_id");
        initRecyclerView();
        if (CheckInternet.isConnectingToInternet(this)) {
            getData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rvContraction = findViewById(R.id.rv_contraction);
        progress = findViewById(R.id.progress);
    }

    public void initRecyclerView() {
        rvContraction.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvContraction.setLayoutManager(mLayoutManager);
    }

    private void getData() {
        progress.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiLink.URL + "contraction_get.php?user_id=" + user_id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        LapItem lapItem = new LapItem();
                        lapItem.setDate(jsonObject.getLong("c_date"));
                        lapItem.setDuration(jsonObject.getString("duration"));
                        lapItem.setInterval(jsonObject.getLong("interval_time"));
                        lapItems.add(lapItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                    }
                }
                contractionAdapter = new ContractionAdapter(ContractionDetailsActivity.this, lapItems);
                rvContraction.setAdapter(contractionAdapter);
                contractionAdapter.notifyDataSetChanged();
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
