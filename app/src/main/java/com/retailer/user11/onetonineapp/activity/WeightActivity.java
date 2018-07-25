package com.retailer.user11.onetonineapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*

public class WeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
    }
}
*/
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.retailer.user11.onetonineapp.adapter.CustomerRecyclerAdapter;
import com.retailer.user11.onetonineapp.adapter.WeightAdapter;
import com.retailer.user11.onetonineapp.model.LapItem;
import com.retailer.user11.onetonineapp.model.UserModel;
import com.retailer.user11.onetonineapp.model.WeightItem;
import com.retailer.user11.onetonineapp.utils.ApiLink;
import com.retailer.user11.onetonineapp.utils.CheckInternet;
import com.retailer.user11.onetonineapp.utils.SessionManager;
import com.retailer.user11.onetonineapp.utils.SimpleDividerItemDecoration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class WeightActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected ProgressBar progress;
    RecyclerView recyclerView;
    WeightAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<WeightItem> data;
    RequestQueue rq;
    SessionManager sessionManager;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_weight);

        rq = Volley.newRequestQueue(this);
        initView();
       /* setSupportActionBar(toolbar);
        toolbar.setTitle("");*/
        initRecyclerVIew();
        user_id = getIntent().getStringExtra("user_id");
        sessionManager = new SessionManager(this);
        data = new ArrayList<>();
        if (CheckInternet.isConnectingToInternet(this)) {
            getData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        /*  initView();
        lapItems = new ArrayList<>();
        user_id = getIntent().getStringExtra("user_id");
      //  Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
        initRecyclerView();*/

        if (CheckInternet.isConnectingToInternet(this)) {
            getData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerVIew() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, SimpleDividerItemDecoration.VERTICAL_LIST, 2));
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getData() {
        progress.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiLink.URL+"get_weight_display.php?user_id="+user_id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                data.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        WeightItem personUtils = new WeightItem();
                        personUtils.setGet_date(jsonObject.getString("created_date"));
                        personUtils.setGet_weight(jsonObject.getString("current_weight"));
                     //   personUtils.setWeek(jsonObject.getString("week"));
                        personUtils.setGet_difference(jsonObject.getString("gain_loss"));
//                        personUtils.setGet_difference(jsonObject.getString("name"));

                        data.add(personUtils);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                    }
                }
                mAdapter = new WeightAdapter(WeightActivity.this, data);
                recyclerView.setAdapter(mAdapter);
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


  /*  @Override
    public void onBackPressed() {
    Intent i=new Intent(getApplicationContext(),CustomerDetailsActivity.class);
    startActivity(i);
    finish();
*/
      /*  new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).create().show();*/


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                // searchViewCollapsed();
                mAdapter.setFilter(data);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                //  searchViewExpanded();
                return true; // Return true to expand action view
            }
        });
        return true;
    }*/

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView_offer);
        toolbar = findViewById(R.id.toolbar);
        progress = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                new AlertDialog.Builder(this)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you want to Log Out App?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent mIntent = new Intent(WeightActivity.this, CustomerDetailsActivity.class);
                                sessionManager.logoutUser();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mIntent);
                                finish();
                            }
                        }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  /*  @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<UserModel> filteredModelList = filter(data, newText);
        mAdapter.setFilter(filteredModelList);
        return false;
    }*/


    private ArrayList<UserModel> filter(ArrayList<UserModel> models, String query) {
        query = query.toString();

        final ArrayList<UserModel> filteredModelList = new ArrayList<>();
        for (UserModel model : models) {
            final String text = model.getName().toString();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    /*private void initView() {
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
        Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://resourcedrop.co.in/onetonine/get_my_weight_all_display.php?id=54", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        WeightItem lapItem = new WeightItem();
                        lapItem.setGet_date(jsonObject.getString("get_date"));
                        lapItem.setGet_weight(jsonObject.getString("get_weight"));
                        lapItem.setWeek(jsonObject.getString("week"));
                        lapItem.setGet_difference(jsonObject.getString("get_difference"));
                        lapItems.add(lapItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                    }
                }
                contractionAdapter = new WeightAdapter(WeightActivity.this,lapItems);
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
                } else if (volleyError instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!",
                            Toast.LENGTH_LONG).show();
                } else if (volleyError instanceof NoConnectionError) {
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
*/

}
