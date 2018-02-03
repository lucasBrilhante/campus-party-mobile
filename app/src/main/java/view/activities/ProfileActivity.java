package view.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cpbr11.campuseromobile.R;
import presenter.IntermediateAuthRequests;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);

        TextView name = findViewById(R.id.user_name);
        TextView email = findViewById(R.id.e_mail);
        TextView local = findViewById(R.id.local);
        TextView about = findViewById(R.id.about);
        TextView info = findViewById(R.id.user_info);
        ImageView github = findViewById(R.id.github);
        ImageView insta = findViewById(R.id.insta);
        ImageView linkedin = findViewById(R.id.linkedin);
        ImageView twitter = findViewById(R.id.twitter);
        ImageView face = findViewById(R.id.face);
        LinearLayout emailBox = findViewById(R.id.email_box);
        LinearLayout localBox = findViewById(R.id.local_box);

        RequestQueue queue = Volley.newRequestQueue(this);

        IntermediateAuthRequests.updateTokenAndFillUserProfile(this, queue, name, info, email,
                local, about, github, insta, linkedin, face, twitter, emailBox, localBox);

        super.onCreate(savedInstanceState);
    }

}
