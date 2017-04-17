package com.example.android.smartfuelindicator;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.android.smartfuelindicator.R.id.tv1;

public class FuelHistory extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history);

        int textViewCount = 15;

        t1 = (TextView) findViewById(R.id.tv1);
        t2 = (TextView) findViewById(R.id.tv2);
        t3 = (TextView) findViewById(R.id.tv3);
        t4 = (TextView) findViewById(R.id.tv4);
        t5 = (TextView) findViewById(R.id.tv5);
        t6 = (TextView) findViewById(R.id.tv6);
        t7 = (TextView) findViewById(R.id.tv7);
        t8 = (TextView) findViewById(R.id.tv8);
        t9 = (TextView) findViewById(R.id.tv9);
        t10 = (TextView) findViewById(R.id.tv10);


        final TextView[] textViewArray = new TextView[textViewCount];

       for(i = 0; i < textViewCount; i++) {
            textViewArray[i] = new TextView(this);
        }

        textViewArray[0]=t1;
        textViewArray[1]=t2;
        textViewArray[2]=t3;
        textViewArray[3]=t4;
        textViewArray[4]=t5;
        textViewArray[5]=t6;
        textViewArray[6]=t7;
        textViewArray[7]=t8;
        textViewArray[8]=t9;
        textViewArray[9]=t10;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    JSONObject jsonResponse = new JSONObject(response);
//                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray array = new JSONArray(response);

                    for(int ij=0;ij<array.length();ij++) {
                        JSONArray first = array.getJSONArray(ij);
                        boolean success = first.getBoolean(0);
                        if (success) {
                            new AlertDialog.Builder(FuelHistory.this).setMessage("Fuel Obtained").create().show();
                            for (i = 0; i < 10; i++) {
                                textViewArray[i].setText(first.getString(1));
                                i++;
                                textViewArray[i].setText(" "+ first.getDouble(2) + "");
                            }
                        } else {
                            new AlertDialog.Builder(FuelHistory.this).setMessage("Fuel Not Obtained").create().show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        HistoryRequest historyRequest = new HistoryRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(FuelHistory.this);
        queue.add(historyRequest);

    }
}
