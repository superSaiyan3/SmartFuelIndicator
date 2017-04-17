package com.example.android.smartfuelindicator;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by viscabarca on 17/04/17.
 */

public class HistoryRequest extends StringRequest {
    private static final String HISTORY_REQUEST_URL = "http://192.168.1.100/history.php";
    private Map<String, String> params;

    public HistoryRequest(Response.Listener<String> listener) {
        super(Method.POST, HISTORY_REQUEST_URL, listener, null);
        params = new HashMap<>();

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

