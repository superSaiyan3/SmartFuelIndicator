package com.example.android.smartfuelindicator;

/**
 * Created by viscabarca on 16/04/17.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MileageRequest extends StringRequest {
    private static final String MILEAGE_REQUEST_URL = "http://192.168.1.100/carMil.php";
    private Map<String, String> params;

    public MileageRequest(String carModel, Response.Listener<String> listener) {
        super(Method.POST, MILEAGE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("carname",carModel);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

