package com.retailer.user11.onetonineapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.adapter.CustomerRecyclerAdapter;
import com.retailer.user11.onetonineapp.adapter.KickHistoryAdapter;
import com.retailer.user11.onetonineapp.model.KickItem;
import com.retailer.user11.onetonineapp.model.UserModel;
import com.retailer.user11.onetonineapp.utils.ApiLink;
import com.retailer.user11.onetonineapp.utils.CheckInternet;
import com.retailer.user11.onetonineapp.utils.SessionManager;
import com.retailer.user11.onetonineapp.utils.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    protected Toolbar toolbar;
    protected ProgressBar progress;
    RecyclerView recyclerView;
    CustomerRecyclerAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserModel> data;
    RequestQueue rq;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        rq = Volley.newRequestQueue(this);
        initView();
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        initRecyclerVIew();
        sessionManager = new SessionManager(this);
        data = new ArrayList<>();
        if (CheckInternet.isConnectingToInternet(this)) {
            getData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void initRecyclerVIew() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, SimpleDividerItemDecoration.VERTICAL_LIST, 2));
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getData() {
        progress.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( ApiLink.URL+"register_admin.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        UserModel personUtils = new UserModel();
                        personUtils.setId(jsonObject.getString("id"));
                        personUtils.setName(jsonObject.getString("name"));
                        personUtils.setEmail(jsonObject.getString("email"));
                        personUtils.setBloodgroup(jsonObject.getString("bloodgroup"));
                        personUtils.setAge(jsonObject.getString("age"));
                        personUtils.setDob(jsonObject.getString("dob"));
                        personUtils.setStatus(jsonObject.getString("status"));
                        personUtils.setmobile(jsonObject.getString("mobileno"));
                        data.add(personUtils);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                    }
                }
                mAdapter = new CustomerRecyclerAdapter(MainActivity.this, data);
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


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).create().show();
    }

    @Override
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
    }

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
                                Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<UserModel> filteredModelList = filter(data, newText);
        mAdapter.setFilter(filteredModelList);
        return false;
    }


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
}
