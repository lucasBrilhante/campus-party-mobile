package view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cpbr11.campuseromobile.R;
import presenter.IntermediateAuthRequests;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView name = findViewById(R.id.user_name);

        RequestQueue queue = Volley.newRequestQueue(this);

        IntermediateAuthRequests.updateTokenAndFillUserProfile(this, queue, name);

        super.onCreate(savedInstanceState);
    }

}
