package presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cpbr11.campuseromobile.VolleyCallback;

public class IntermediateAuthRequests {
    private static final String REDIRECT_URL = "https://campuseroMobile.com/callback";
    private static final String CLIENT_ID = "q0FbZjHAvlB4dxQp8cNpWrK3X85BxSuBq4NgARPf";
    private static final String SECRET_KEY = "19QXI8Et5YM4ktmsapdsW5JWtIVObaw0VixSV06shSpqMXkW" +
            "XnFhezONi0Y1D5w8bwd9Qmd7dsmiMxDH6gLCnj6APSXDkAGTbaMo30fd6oi" +
            "x4Tm6Ny47hA7CaF2GGcPm";

    public static boolean isUserLoggedIn(Context context) {
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

            System.out.println("Atualizei: " + accessToken);

            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void authenticateUser(final Context context, RequestQueue queue,
                                        final String code) {
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
                IntermediateAuthRequests.saveToken(context, response);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        };

        HttpUtil.PostRequest(context, queue, volleyCallback, URL, header_params, post_params);
    }

    /*public static void updateTokenAndExecuteAction(final Context context, final RequestQueue queue, final String TAG) {
        final String URL = "https://sandboxaccounts.campuse.ro/o/token/";

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        String refreshToken = sharedPref.getString("refresh_token", "Empty");

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
                IntermediateAuthRequests.saveToken(context, response);

                if (TAG.equals("get_profile")) {
                    UserProfilePresenter userProfilePresenter = new UserProfilePresenter(context, queue);
                    userProfilePresenter.fillProfile();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        };

        HttpUtil.PostRequest(context, queue, volleyCallback, URL, header_params, post_params);
    }*/

    public static void updateTokenAndFillUserProfile(final Context context, final RequestQueue queue,
                                                     final TextView nameTextView,
                                                     final TextView userSpecialitiesTextView,
                                                     final TextView emailTextView,
                                                     final TextView localTextView,
                                                     final TextView aboutTextView,
                                                     final TextView userInterestsTextView,
                                                     final ImageView githubImageView,
                                                     final ImageView instImageView,
                                                     final ImageView linkedinImageView,
                                                     final ImageView faceImageView,
                                                     final ImageView twitterImageView,
                                                     final LinearLayout emailBox,
                                                     final LinearLayout localBox) {
        final String URL = "https://sandboxaccounts.campuse.ro/o/token/";

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        String refreshToken = sharedPref.getString("refresh_token", "Empty");

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
                IntermediateAuthRequests.saveToken(context, response);

                UserProfilePresenter userProfilePresenter = new UserProfilePresenter(context, queue,
                        userSpecialitiesTextView, userInterestsTextView, emailTextView, localTextView, aboutTextView, nameTextView,
                        githubImageView, instImageView, linkedinImageView, faceImageView, twitterImageView,
                        emailBox, localBox);
                userProfilePresenter.fillProfile();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        };

        HttpUtil.PostRequest(context, queue, volleyCallback, URL, header_params, post_params);
    }
}
