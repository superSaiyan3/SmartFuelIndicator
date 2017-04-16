package com.example.android.smartfuelindicator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Build.VERSION_CODES.M;

public class Test extends AppCompatActivity {

    Globals g = Globals.getInstance();
    float a;
    ProgressBar pb;
    Button btn ,bMap;
    private TextView msg,progress,percent;
    String cProgress;
    public final static String MESSAGE_KEY="com.example.android.smartfuelindicator.key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent tIntent = getIntent();
        int currentProgress= tIntent.getIntExtra(MESSAGE_KEY,0);

        cProgress = String.valueOf(currentProgress);

        btn = (Button) findViewById(R.id.bConn);
        msg = (TextView) findViewById(R.id.tvJsonContent);
        progress = (TextView) findViewById(R.id.tvProgress);
        pb = (ProgressBar) findViewById(R.id.pb2);
        percent = (TextView) findViewById(R.id.tvPercent);
        bMap = (Button) findViewById(R.id.bMap);

        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new JSONtask().execute("http://localhost/phpinfo.php");
                }
            });
        }
    }


    class JSONtask extends AsyncTask<String,String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String inputLine;

                while ((inputLine = reader.readLine()) != null) {

                    a=Float.parseFloat(reader.readLine());
                    buffer.append(inputLine);
                }

                return buffer.toString();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            g.setFuelValue(a);
          //  msg.setText("Mileage"+g.getMileage()+"");
            progress.setText("Volume = "+ g.getFuelValue() + "Litres");
            g.setModel("Maruti Suzuki celerio");
            int value = (int) ((a/7)*100);
            float percentValue = (a/7)*100;
            /*String str = percentValue+"";
            str=str.substring(0,6);
            float newPercentVal = Float.parseFloat(str);*/
            pb.setProgress(value);
            percent.setText("Percentage: " + percentValue + "%");   //newPercentVal

            bMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mapIntent = new Intent(Test.this, BufferActivity.class);
                    startActivity(mapIntent);
                }
            });

        }
    }
}
