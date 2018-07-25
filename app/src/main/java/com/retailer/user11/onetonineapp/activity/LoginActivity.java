package com.retailer.user11.onetonineapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.utils.SessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText edtUserEmail;
    protected EditText edtUserPassword;
    protected TextView txLogin;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
        sessionManager = new SessionManager(this);
    }

    private void initView() {
        edtUserEmail = findViewById(R.id.edt_user_email);
        edtUserPassword = findViewById(R.id.edt_user_password);
        txLogin = findViewById(R.id.tx_login);
        txLogin.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tx_login) {
            String username = edtUserEmail.getText().toString();
            String password = edtUserPassword.getText().toString();
            // Check if username, password is filled
            if (username.trim().length() > 0 && password.trim().length() > 0) {
                if (username.equals("admin") && password.equals("a3799")) {
                    sessionManager.createLoginSession("admin", "a3799");
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(this, "Login failed Username/Password is incorrect", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
        }

}
}
