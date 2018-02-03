package presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cpbr11.campuseromobile.VolleyCallback;

/**
 * Created by igor on 02/02/18.
 */

public class HttpUtil {
    public static void PostRequest(Context context, RequestQueue queue,
                                   final VolleyCallback volleyCallback,
                                   final String URL,
                                   final Map<String,String> headers,
                                   final Map<String,String> params) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallback.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyCallback.onErrorResponse(error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    public static void GetRequest(Context context, RequestQueue queue,
                                  final VolleyCallback volleyCallback,
                                  final String URL, final Map<String,String> headers) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallback.onResponse(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyCallback.onErrorResponse(error);
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }

        };

        queue.add(getRequest);
    }
}
