package presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cpbr11.campuseromobile.VolleyCallback;

/**
 * Created by igor on 02/02/18.
 */

public class AuthenticateUserPresenter {
    private static final String REDIRECT_URL = "https://campuseroMobile.com/callback";
    private static final String CLIENT_ID = "q0FbZjHAvlB4dxQp8cNpWrK3X85BxSuBq4NgARPf";
    private static final String SECRET_KEY = "19QXI8Et5YM4ktmsapdsW5JWtIVObaw0VixSV06shSpqMXkW" +
            "XnFhezONi0Y1D5w8bwd9Qmd7dsmiMxDH6gLCnj6APSXDkAGTbaMo30fd6oi" +
            "x4Tm6Ny47hA7CaF2GGcPm";

    private static boolean isUserLoggedIn(Context context) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        boolean loggedIn = sharedPref.getBoolean("logged_in", false);

        return loggedIn;
    }

    public static void saveToken(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            String accessToken = jsonObject.getString("access_token");
            String refreshToken = jsonObject.getString("refresh_token");

            final SharedPreferences sharedPref =
                    PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putBoolean("logged_in", true);
            editor.putString("access_token", accessToken);
            editor.putString("refresh_token", refreshToken);

            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void authenticateUser(final Context context, final String code) {
        final String URL = "https://sandboxaccounts.campuse.ro/o/token/";

        Map<String,String> post_params = new HashMap<>();
        post_params.put("grant_type","authorization_code");
        post_params.put("code", code);
        post_params.put("client_id", CLIENT_ID);
        post_params.put("client_secret", SECRET_KEY);
        post_params.put("redirect_uri", REDIRECT_URL);

        Map<String,String> header_params = new HashMap<>();
        header_params.put("Accept","application/json");
        header_params.put("Content-Type","application/x-www-form-urlencoded");

        final VolleyCallback volleyCallback = new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response: " + response);
                AuthenticateUserPresenter.saveToken(context, response);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        };

        HttpUtil.PostRequest(context, volleyCallback, URL, header_params, post_params);
    }

    public static void updateToken(final Context context, final String refreshToken) {
        final String URL = "https://sandboxaccounts.campuse.ro/o/token/";

        Map<String,String> post_params = new HashMap<>();
        post_params.put("grant_type","refresh_token");
        post_params.put("refresh_token", refreshToken);
        post_params.put("client_id", CLIENT_ID);

        Map<String,String> header_params = new HashMap<>();
        header_params.put("Accept","application/json");
        header_params.put("Content-Type","application/x-www-form-urlencoded");

        final VolleyCallback volleyCallback = new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response: " + response);
                AuthenticateUserPresenter.saveToken(context, response);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        };

        HttpUtil.PostRequest(context, volleyCallback, URL, header_params, post_params);
    }
}
