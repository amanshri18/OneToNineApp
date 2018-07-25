package com.retailer.user11.onetonineapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.utils.ApiLink;
import com.retailer.user11.onetonineapp.utils.CheckInternet;
import com.retailer.user11.onetonineapp.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView txStatus;
    protected Button btnKick;
    protected Button btnContraction,btnweight;
    TextView cust_name, age, blood_group, dob, email, id,mobileno;
    String user_id, str_bg, str_name, str_age, str_dob, str_email, status,str_mobile;
    Button btn_active, btn_deactivate,btn_delete;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_customer_details);
        initView();
        user_id = getIntent().getStringExtra("id");
        str_name = getIntent().getStringExtra("name");
        str_bg = getIntent().getStringExtra("blood_group");
        str_dob = getIntent().getStringExtra("dob");
        str_age = getIntent().getStringExtra("age");
        str_email = getIntent().getStringExtra("email");
        status = getIntent().getStringExtra("status");//mobile_number
        str_mobile = getIntent().getStringExtra("mobile_number");

        cust_name.setText(str_name);
        blood_group.setText(str_bg);
        dob.setText(str_dob);
        age.setText(str_age);
        email.setText(str_email);
        txStatus.setText(status);
        mobileno.setText(str_mobile);

        if (txStatus.getText().toString().equalsIgnoreCase("inactive")) {
            btn_active.setVisibility(View.VISIBLE);
            btn_deactivate.setVisibility(View.GONE);
            btnKick.setVisibility(View.GONE);
            btnContraction.setVisibility(View.GONE);
            btnweight.setVisibility(View.GONE);

        } else if (txStatus.getText().toString().equalsIgnoreCase("active")) {
            btn_active.setVisibility(View.GONE);
            btn_deactivate.setVisibility(View.VISIBLE);
            btnKick.setVisibility(View.VISIBLE);
            btnContraction.setVisibility(View.VISIBLE);
            btnweight.setVisibility(View.VISIBLE);

        } else if (txStatus.getText().toString().equalsIgnoreCase("")) {
            btn_active.setVisibility(View.VISIBLE);
            btn_deactivate.setVisibility(View.GONE);
            btnKick.setVisibility(View.GONE);
            btnContraction.setVisibility(View.GONE);
            btnweight.setVisibility(View.GONE);
        }
    }

    public void dActivationDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerDetailsActivity.this);
        alertDialog.setTitle("Confirm DeActivation...");
        alertDialog.setMessage("Are you sure you want DeActivate this Customer?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deActivationProcess();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    public void activationDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerDetailsActivity.this);
        alertDialog.setTitle("Confirm Activation...");
        alertDialog.setMessage("Are you sure you want Activate this Customer?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                customerActivation();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void customerActivation() {

        Utils.showProgressDialog(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiLink.URL + "set_type.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog();
                        try {
                            JSONArray categoey_array = new JSONArray(response);//put here your json string
                            JSONObject jsonObject = categoey_array.getJSONObject(0);
                            String pare = jsonObject.getString("status");
                            if (pare.trim().equalsIgnoreCase("activated")) {
                                //
                                try {
                                    txStatus.setText("active");
                                    btn_active.setVisibility(View.GONE);
                                    btn_deactivate.setVisibility(View.VISIBLE);
                                    btnKick.setVisibility(View.VISIBLE);
                                    btnContraction.setVisibility(View.VISIBLE);
                                    btnweight.setVisibility(View.VISIBLE);
                                    Toast.makeText(CustomerDetailsActivity.this, "Activation Successful", Toast.LENGTH_LONG).show();
                                    Log.e("response1 : ", response.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            } else {
                                Toast.makeText(CustomerDetailsActivity.this, "failed", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        Utils.hideProgressDialog();
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
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", user_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void deActivationProcess() {
        Utils.showProgressDialog(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiLink.URL + "inactive_user.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog();
                        try {
                            JSONArray jsonArray = new JSONArray(response);//put here your json string
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String pare = jsonObject.getString("status");
                            if (pare.trim().equalsIgnoreCase("inactivated")) {
                                //
                                try {
                                    txStatus.setText("inactive");
                                    btn_active.setVisibility(View.VISIBLE);
                                    btn_deactivate.setVisibility(View.GONE);
                                    btnKick.setVisibility(View.GONE);
                                    btnContraction.setVisibility(View.GONE);
                                    btnweight.setVisibility(View.GONE);
                                    Toast.makeText(CustomerDetailsActivity.this, "De Activation Successful", Toast.LENGTH_LONG).show();
                                    Log.e("response1 : ", response.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(CustomerDetailsActivity.this, "failed", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        Utils.hideProgressDialog();
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
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", user_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void initView() {
        cust_name = findViewById(R.id.cust_name);
        email = findViewById(R.id.email);
        blood_group = findViewById(R.id.blood_group);
        age = findViewById(R.id.age);
        mobileno = findViewById(R.id.tx_mobile);
        dob = findViewById(R.id.dob);
        btn_active = findViewById(R.id.btnactive);
        btn_active.setOnClickListener(this);
        btn_deactivate = findViewById(R.id.btndeactive);
        btn_deactivate.setOnClickListener(this);
        btn_delete = findViewById(R.id.btndelete);
        btn_delete.setOnClickListener(this);
        txStatus = findViewById(R.id.tx_status);
        btnKick = findViewById(R.id.btn_kick);
        btnKick.setOnClickListener(CustomerDetailsActivity.this);
        btnContraction = findViewById(R.id.btn_contraction);
        btnContraction.setOnClickListener(CustomerDetailsActivity.this);
        btnweight=findViewById(R.id.btn_weight);
        btnweight.setOnClickListener(CustomerDetailsActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnactive) {
            if (CheckInternet.isConnectingToInternet(CustomerDetailsActivity.this)) {
                activationDialog();
            } else {
                Toast.makeText(CustomerDetailsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btndeactive) {
            if (CheckInternet.isConnectingToInternet(CustomerDetailsActivity.this)) {
                dActivationDialog();
            } else {
                Toast.makeText(CustomerDetailsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btn_kick) {
            mIntent = new Intent(CustomerDetailsActivity.this, KickDetailsActivity.class);
            mIntent.putExtra("user_id", user_id);
            startActivity(mIntent);
        } else if (view.getId() == R.id.btn_contraction) {
            mIntent = new Intent(CustomerDetailsActivity.this, ContractionDetailsActivity.class);
            mIntent.putExtra("user_id", user_id);
            startActivity(mIntent);
        }
        else if(view.getId()==R.id.btn_weight){
            mIntent = new Intent(CustomerDetailsActivity.this, WeightActivity.class);
            mIntent.putExtra("user_id", user_id);
            startActivity(mIntent);
        }
        else if (view.getId() == R.id.btndelete) {
          DeleteOption();
        }

    }
    public  void DeleteOption(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,ApiLink.URL+"delete_user.php?user_id="+user_id ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent i=new Intent(CustomerDetailsActivity.this,MainActivity.class);
                        Toast.makeText(CustomerDetailsActivity.this, "Delete Patient", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        Utils.hideProgressDialog();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerDetailsActivity.this);
        requestQueue.add(stringRequest);

    }
}
