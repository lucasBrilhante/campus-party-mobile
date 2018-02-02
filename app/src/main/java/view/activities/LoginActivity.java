package view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    String client_id = "q0FbZjHAvlB4dxQp8cNpWrK3X85BxSuBq4NgARPf";
    String secret_key = "19QXI8Et5YM4ktmsapdsW5JWtIVObaw0VixSV06shSpqMXkW" +
            "XnFhezONi0Y1D5w8bwd9Qmd7dsmiMxDH6gLCnj6APSXDkAGTbaMo30fd6oi" +
            "x4Tm6Ny47hA7CaF2GGcPm";

    private void postRequest(String url, final String authCode) {
        System.out.println(url);
        System.out.println(authCode);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //this is the url where you want to send the request

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LoginActivity", "Response: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LoginActivity", "Error: " + error);
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "authorization_code");
                params.put("redirect_uri", "https://campuseroMobile.com/callback/");
                params.put("code", authCode);

                System.out.println(params);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "<client_id>", "<secret_key>").getBytes(), Base64.DEFAULT)));
                params.put("client_id" , client_id);
                params.put("secret_key" , secret_key);

                System.out.println(params);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.email_sign_in_button) {
            String url = "https://sandboxaccounts.campuse.ro/o/authorize/?" +
                    "response_type=code&client_id=q0FbZjHAvlB4dxQp8c" +
                    "NpWrK3X85BxSuBq4NgARPf&redirect_uri=https://campuseroMobile.com/callback";

            final WebView webview = new WebView(this);

            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        String regex = "code=(.+)";
                        Pattern p = Pattern.compile(regex);

                        Matcher m = p.matcher(url);

                        if (m.find()) {
                            Log.v("LoginActivity", "code: " + m.group(1));

                            Intent after_login_intent = new Intent(getApplicationContext(),
                                    LoginActivity.class);

                            after_login_intent.putExtra("code", "example_code");

                            //String post_url = "https://" + client_id + ":" + secret_key + "@sandboxaccounts.campuse.ro/o/token";
                            String post_url = "https://sandboxaccounts.campuse.ro/o/token";

                            Log.v("LoginActivity", "Url: " + post_url);

                            postRequest(post_url, m.group(1));

                            startActivity(after_login_intent);
                        }

                    return super.shouldOverrideUrlLoading(view, url);
                }
            });

            setContentView(webview);
            webview.loadUrl(url);
        }
    }
}

