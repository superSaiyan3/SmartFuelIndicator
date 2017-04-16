package com.example.android.smartfuelindicator;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import static com.example.android.smartfuelindicator.R.id.etName;
import static com.example.android.smartfuelindicator.R.id.etPhoneNo;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etDeviceName = (EditText) findViewById(etName);
        final EditText etDeviceID = (EditText) findViewById(R.id.etDeviceID);
        final EditText etCarModel = (EditText) findViewById(R.id.etCarModel);
        final EditText etYear = (EditText) findViewById(R.id.etYear);
        final EditText etPhoneNo = (EditText) findViewById(R.id.etPhoneNo);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);


        if (bRegister != null) {
            bRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int deviceId = Integer.parseInt(etDeviceID.getText().toString());
                    final String deviceName = etDeviceName.getText().toString();
                    final String carModel = etCarModel.getText().toString();
                    final int yop = Integer.parseInt(etYear.getText().toString());
                    final int phoneNo = Integer.parseInt(etPhoneNo.getText().toString());
                    final String password = etPassword.getText().toString();
                    
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(Register.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(deviceId, deviceName, carModel, yop ,phoneNo, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    queue.add(registerRequest);
                }
            });
        }
    }
}

