package view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Credentials;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import cpbr11.campuseromobile.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(this);
    }

    final String REDIRECT_URL = "https://campuseroMobile.com/callback";

    final String CLIENT_ID = "q0FbZjHAvlB4dxQp8cNpWrK3X85BxSuBq4NgARPf";
    final String SECRET_KEY = "19QXI8Et5YM4ktmsapdsW5JWtIVObaw0VixSV06shSpqMXkW" +
            "XnFhezONi0Y1D5w8bwd9Qmd7dsmiMxDH6gLCnj6APSXDkAGTbaMo30fd6oi" +
            "x4Tm6Ny47hA7CaF2GGcPm";

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.email_sign_in_button) {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(ServiceGenerator.API_BASE_URL + "/authorize" + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=code"));
            startActivity(intent);
        }
    }

    private void authenticateUser(final VolleyCallback volleyCallback, final String code) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = "https://sandboxaccounts.campuse.ro/o/token/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
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
        System.out.println("here");

        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        System.out.println(uri);
        if (uri != null && uri.toString().startsWith(REDIRECT_URL.toLowerCase())) {
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            System.out.println("Got code: " + code);
            System.out.println("Here");
            if (code != null) {
                System.out.println("Code: " + code);
                // get access token
                /*LoginService loginService =
                        ServiceGenerator.createService(LoginService.class, CLIENT_ID, SECRET_KEY);
                Log.v("Passei", "passei");

                //Call<AccessToken> call = loginService.getAccessToken(code, REDIRECT_URL, "authorization_code");
                Log.v("Passei2", "Passei2");

                Call<AccessToken> call = loginService.getAccessToken(code, REDIRECT_URL, "authorization_code");

                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                            System.out.println("Error: " + response.raw());
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        // Handle error
                    }
                });*/
                authenticateUser(new VolleyCallback() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response: " + response);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("Falhou");
                    }
                }, code);
            } else if (uri.getQueryParameter("error") != null) {
                // show an error message here
            }
        }
    }
}

