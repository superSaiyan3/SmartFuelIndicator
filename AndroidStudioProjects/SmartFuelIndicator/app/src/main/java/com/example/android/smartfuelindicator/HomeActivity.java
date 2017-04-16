package com.example.android.smartfuelindicator;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.android.smartfuelindicator.R.id.bLogin;
import static com.example.android.smartfuelindicator.R.id.etDeviceID;
import static com.example.android.smartfuelindicator.R.id.etPassword;
import static com.example.android.smartfuelindicator.R.id.start;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final EditText etDeviceID = (EditText) findViewById(R.id.etDeviceID);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button btSkip = (Button) findViewById(R.id.bSkip);

        final TextView RegisterLink = (TextView) findViewById(R.id.tvRegister);


                RegisterLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent regIntent = new Intent(HomeActivity.this, Register.class);
                        startActivity(regIntent);
                    }
                });


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int deviceId = Integer.parseInt(etDeviceID.getText().toString());
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                new AlertDialog.Builder(HomeActivity.this).setMessage("Login Successful").create().show();
                                Intent intent = new Intent(HomeActivity.this, Test.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(deviceId, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                queue.add(loginRequest);


            }
        });

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skipIntent = new Intent(HomeActivity.this, Test.class);
                startActivity(skipIntent);
            }
        });
    }
}




