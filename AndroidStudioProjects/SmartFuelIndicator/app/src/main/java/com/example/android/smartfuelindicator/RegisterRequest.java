package com.example.android.smartfuelindicator;

/**
 * Created by viscabarca on 15/04/17.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.1.100/register.php";
    private Map<String, String> params;

    public RegisterRequest(int deviceId, String deviceName, String carModel ,int yop, int phoneNo, String password, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("deviceId", deviceId+ "");
        params.put("deviceName", deviceName);
        params.put("carModel", carModel);
        params.put("yop",yop +"");
        params.put("phoneNo", phoneNo + "");
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
