package com.example.android.smartfuelindicator;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.android.smartfuelindicator.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Globals g = Globals.getInstance();
    float fuelReading;
    double carMileage,totalDist;
    private GoogleMap mMap;
    static final int REQUEST_LOCATION = 1;
    int year;
    TextView txtTotalDist;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        txtTotalDist = (TextView) findViewById(R.id.tvMileage);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    g.setMileage(jsonResponse.getDouble("mileage"));
                    year = jsonResponse.getInt("yom");
                    if(success) {
                        txtTotalDist.setText("Distance to zero:"+Math.round(g.getFuelValue()*g.getMileage())+"km");
                        fuelReading=g.getFuelValue();
                        carMileage = g.getMileage();
                        totalDist = fuelReading*carMileage*1000;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MileageRequest mileageRequest = new MileageRequest(g.getModel(),responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        queue.add(mileageRequest);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            // Add a marker in Bangalore and move the camera
            LatLng bangalore = new LatLng(12.8615, 77.6647);
            mMap.addMarker(new MarkerOptions().position(bangalore).title("Vehicle Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bangalore, 8));
            mMap.setMyLocationEnabled(true);
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(12.8615, 77.6647))
                    .radius(1000) // In meters
                    .strokeColor(Color.RED)
                    .fillColor(Color.LTGRAY);

            // Get back the mutable Circle
            Circle circle = mMap.addCircle(circleOptions);



        }
    }
}

