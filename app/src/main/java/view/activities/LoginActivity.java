package view.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import cpbr11.campuseromobile.R;
import presenter.IntermediateAuthRequests;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    final String API_BASE_URL = "https://sandboxaccounts.campuse.ro/o/";
    final String REDIRECT_URL = "https://campuseroMobile.com/callback";
    final String CLIENT_ID = "q0FbZjHAvlB4dxQp8cNpWrK3X85BxSuBq4NgARPf";

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(IntermediateAuthRequests.isUserLoggedIn(this)) {
            Intent intent = new Intent(this, AgendaActivity.class);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_login);

            queue = Volley.newRequestQueue(this);

            Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(this);
        }
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

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(REDIRECT_URL.toLowerCase())) {
            String code = uri.getQueryParameter("code");

            if (code != null) {
                System.out.println("Code: " + code);

                IntermediateAuthRequests.authenticateUser(this, queue, code);

                Intent intent = new Intent(this, AgendaActivity.class);
                startActivity(intent);

            } else if (uri.getQueryParameter("error") != null) {
                System.out.println("Error");
            }
        }
    }
}

