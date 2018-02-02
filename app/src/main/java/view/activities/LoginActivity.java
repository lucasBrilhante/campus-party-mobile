package view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import cpbr11.campuseromobile.R;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    final String API_BASE_URL = "https://sandboxaccounts.campuse.ro/o/";
    final String REDIRECT_URL = "https://campuseroMobile.com/callback";
    final String CLIENT_ID = "q0FbZjHAvlB4dxQp8cNpWrK3X85BxSuBq4NgARPf";
    final String SECRET_KEY = "19QXI8Et5YM4ktmsapdsW5JWtIVObaw0VixSV06shSpqMXkW" +
            "XnFhezONi0Y1D5w8bwd9Qmd7dsmiMxDH6gLCnj6APSXDkAGTbaMo30fd6oi" +
            "x4Tm6Ny47hA7CaF2GGcPm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.email_sign_in_button) {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(API_BASE_URL + "/authorize" +
                            "?client_id=" + CLIENT_ID + "&redirect_uri=" +
                            REDIRECT_URL + "&response_type=code"));
            startActivity(intent);
        }
    }

    private void authenticateUser(final VolleyCallback volleyCallback, final String code) {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String URL_TOKEN = "https://sandboxaccounts.campuse.ro/o/token/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_TOKEN,
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
                Map<String,String> params=new HashMap<String,String>();
                params.put("grant_type","authorization_code");
                params.put("code", code);
                params.put("client_id", CLIENT_ID);
                params.put("client_secret", SECRET_KEY);
                params.put("redirect_uri", REDIRECT_URL);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<String,String>();
                headers.put("Accept","application/json");
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(REDIRECT_URL.toLowerCase())) {
            String code = uri.getQueryParameter("code");

            if (code != null) {
                System.out.println("Code: " + code);

                authenticateUser(new VolleyCallback() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response: " + response);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("Error");
                    }
                }, code);
            } else if (uri.getQueryParameter("error") != null) {
                System.out.println("Error");
            }
        }
    }
}

