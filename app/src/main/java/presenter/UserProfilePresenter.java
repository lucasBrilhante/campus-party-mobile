package presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telecom.Call;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpbr11.campuseromobile.VolleyCallback;
import model.Profile;

/**
 * Created by igor on 03/02/18.
 */

public class UserProfilePresenter {
    private Context context;
    private RequestQueue requestQueue;

    public UserProfilePresenter(Context context, RequestQueue queue) {
        this.context = context;
        this.requestQueue = queue;
    }

    public void fillProfile() {
        String URL = "https://sandboxapi.campuse.ro/user/myprofile/";

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String,String> header_params = new HashMap<>();
        header_params.put("Accept","application/json");
        header_params.put("Content-Type","application/x-www-form-urlencoded");
        header_params.put("Authorization", "Bearer " + sharedPref.getString("access_token", "EMPTY"));

        final VolleyCallback volleyCallback = new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response: " + response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    String username = jsonObject.getString("username");
                    String email = jsonObject.getString("email");
                    String name = jsonObject.getString("first_name");
                    String lastName = jsonObject.getString("last_name");
                    String gender = jsonObject.getString("gender");
                    String occupation = jsonObject.getString("occupation");
                    String primaryRole = jsonObject.getString("primary_role");
                    String dateOfBirth = jsonObject.getString("date_of_birth");
                    String github = jsonObject.getString("github");
                    String instagram = jsonObject.getString("instagram");
                    String twitter = jsonObject.getString("twitter");
                    String facebook = jsonObject.getString("facebook");
                    String flickr = jsonObject.getString("flickr");
                    String linkedin = jsonObject.getString("linkedin");
                    String googleplus = jsonObject.getString("googleplus");
                    String whatsapp = jsonObject.getString("whatsapp");
                    String phoneNumber = jsonObject.getString("phone_number");
                    String mobilePhoneNumber = jsonObject.getString("mob_phone_number");
                    String skype = jsonObject.getString("skype");
                    String cityName = jsonObject.getString("city_name");
                    String zipCode = jsonObject.getString("zipcode");
                    String street = jsonObject.getString("street");
                    String streetNumber = jsonObject.getString("street_number");

                    List<String> interestTags = new ArrayList<>();
                    JSONArray interestTagsJsonArray = jsonObject.getJSONArray("interest_tags");

                    for (int i = 0; i < interestTagsJsonArray.length(); i++) {
                        interestTags.add(interestTagsJsonArray.getJSONObject(i).getString("name"));
                    }

                    List <String> specialities = new ArrayList<>();
                    JSONArray specialitiesJsonArray = jsonObject.getJSONArray("specialities");

                    for (int i = 0; i < specialitiesJsonArray.length(); i++) {
                        interestTags.add(specialitiesJsonArray.getJSONObject(i).getString("name"));
                        System.out.println(specialitiesJsonArray.getJSONObject(i).getString("name"));
                    }

                    Profile profile = new Profile(username, email, name, lastName,
                            gender, occupation, interestTags,
                            specialities, primaryRole, dateOfBirth,
                            github, instagram, twitter, facebook,
                            flickr, linkedin, googleplus, whatsapp,
                            phoneNumber, mobilePhoneNumber, skype,
                            cityName, zipCode, street,
                            streetNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        };

        HttpUtil.GetRequest(context, requestQueue, volleyCallback, URL, header_params);
    }
}
