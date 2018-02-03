package presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class UserProfilePresenter {
    private Context context;
    private RequestQueue requestQueue;
    private TextView nameTextView;
    private TextView userSpecialitiesTextView;
    private TextView emailTextView;
    private TextView localTextView;
    private TextView aboutTextView;
    private TextView userInterestsTextView;
    private ImageView githubImageView;
    private ImageView instImageView;
    private ImageView linkedinImageView;
    private ImageView faceImageView;
    private ImageView twitterImageView;
    private LinearLayout emailBox;
    private LinearLayout localBox;

    public UserProfilePresenter(Context context, RequestQueue queue,
                                TextView userSpecialities, TextView userInterestsTextView, TextView emailTextView,
                                TextView localTextView, TextView aboutTextView, TextView nameTextView,
                                ImageView githubImageView, ImageView instImageView,
                                ImageView linkedinImageView, ImageView faceImageView,
                                ImageView twitterImageView, LinearLayout emailBox, LinearLayout localBox) {
        this.context = context;
        this.requestQueue = queue;
        this.nameTextView = nameTextView;
        this.userSpecialitiesTextView = userSpecialities;
        this.emailTextView = emailTextView;
        this.localTextView = localTextView;
        this.aboutTextView = aboutTextView;
        this.githubImageView = githubImageView;
        this.instImageView = instImageView;
        this.linkedinImageView = linkedinImageView;
        this.faceImageView = faceImageView;
        this.twitterImageView = twitterImageView;
        this.emailBox = emailBox;
        this.localBox = localBox;
        this.userInterestsTextView = userInterestsTextView;
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
                    String about = jsonObject.getString("about");

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
                            streetNumber, about);

                    nameTextView.setText(profile.getName() + " " + profile.getLastName());
                    aboutTextView.setText(profile.getAbout());

                    String userEmail = profile.getEmail();
                    setUserInfo(userEmail, emailTextView, emailBox);
                    String userCity = profile.getCityName();
                    setUserInfo(userCity, localTextView, localBox);

                    setUserSocialMedia(github, githubImageView);
                    setUserSocialMedia(instagram, instImageView);
                    setUserSocialMedia(linkedin, linkedinImageView);
                    setUserSocialMedia(facebook, faceImageView);
                    setUserSocialMedia(twitter, twitterImageView);

                    String specialitiesAsString = "";
                    List<String> userSpecialities = profile.getSpecialities();
                    if(!userSpecialities.isEmpty()){
                        for(int i=0; i<userSpecialities.size(); i++) {
                            specialitiesAsString = specialitiesAsString + userSpecialities.get(i);
                            if(i >= 1 && i < userSpecialities.size()-1){
                                specialitiesAsString = specialitiesAsString + ", ";
                            }
                        }

                        userSpecialitiesTextView.setText(specialitiesAsString);
                    }
                    else {
                        userSpecialitiesTextView.setVisibility(View.GONE);
                    }

                    String interestsAsStrings = "";
                    List<String> userInterests = profile.getInterest_tags();
                    if(!userInterests.isEmpty()){
                        for(int i=0; i<userInterests.size(); i++) {
                            interestsAsStrings = interestsAsStrings + userInterests.get(i);
                            if(i >= 1 && i < userInterests.size()-1){
                                interestsAsStrings = interestsAsStrings + ", ";
                            }
                        }

                        userInterestsTextView.setText(interestsAsStrings);
                    }
                    else {
                        userInterestsTextView.setVisibility(View.GONE);
                    }


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

    private void setUserInfo(String info, TextView field, LinearLayout box){
        if(!info.equals("null") && !info.isEmpty()) {
            field.setText(info);
        }
        else {
            box.setVisibility(View.GONE);
        }
    }

    private void setUserSocialMedia(final String mediaLink, ImageView mediaIcon) {
        if(!mediaLink.equals("null") && !mediaLink.isEmpty()){
            mediaIcon.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(mediaLink));
                    context.startActivity(intent);
                }
            });
        }
        else {
            mediaIcon.setVisibility(View.GONE);
        }
    }
}
