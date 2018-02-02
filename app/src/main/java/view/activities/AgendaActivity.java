package view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import cpbr11.campuseromobile.R;
import model.Activity;
import view.SectionsPagerAdapter;
import view.fragments.FilterFragment;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class AgendaActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<ArrayList<Activity>> list_of_days = new ArrayList<>();
        List<Activity> list_of_all_activities = null;

        try {

            list_of_all_activities = new YourAsyncTask(this).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        List<String> dates = new ArrayList<>();

        String dayOld = null;
        int indexSave = -1;

        for(Activity act : list_of_all_activities){
            String dayAct = act.getStartDate().split("T")[0];
            if(!dayAct.equals(dayOld)){
                dates.add(dayAct);
                Log.d("DATE",dayAct);
                list_of_days.add(new ArrayList<Activity>());
                indexSave++;
                dayOld = dayAct;
            }
            list_of_days.get(indexSave).add(act);

        }
        Log.d("List", list_of_days.toString());



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), list_of_days,
                dates, list_of_days.size());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
        tabs.setTabMode(MODE_SCROLLABLE);
        tabs.setupWithViewPager(mViewPager);



    }

    private class YourAsyncTask extends AsyncTask<Void, List<Activity>, List<Activity>> {
        private ProgressDialog dialog;
        private android.app.Activity activity;

        public YourAsyncTask(android.app.Activity activity) {
            this.activity = activity;

        }

        @Override
        protected void onPreExecute() {

        }

        protected List<Activity> doInBackground(Void... args) {

            int condition = 0;
            ArrayList<Activity> list_of_all_activities = new ArrayList<>();
            String url = "https://sandboxapi.campuse.ro/agenda/list/campus-party-brasil-2018/";

            while (condition == 0) {

                JSONObject response = null;
                try {
                    String responseAsString = getJSON(url,30000);
                    response = new JSONObject(responseAsString);
                    Log.d("DEU BOOOOOOOOOOOOOOOOM ", url);
                    int size = response.getJSONArray("results").length();
                    for (int i = 0; i < size; i++) {
                        JSONObject activityJson = response.getJSONArray("results").getJSONObject(i);

                        Activity activity = new Activity(activityJson.getString("slug"),
                                                         activityJson.getString("start_date"),
                                                         activityJson.getString("end_date"),
                                                         activityJson.getString("get_type"));

                        list_of_all_activities.add(activity);
                    }

                    Comparator<Activity> cmp = new Comparator<Activity>() {
                        @Override
                        public int compare(Activity activity1, Activity activity2) {
                            return activity1.getStartDate().compareTo(activity2.getStartDate());
                        }
                    };

                    Collections.sort(list_of_all_activities, cmp);


                    //list_of_days.add(activity_list_of_day);
                    if(response.getString("next") == "null"){
                        Log.d("NEXT","é NULLLLLLLLLLL");
                        condition = 1;
                    }else {
                        url = response.getString("next");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            return list_of_all_activities;
        }
        protected void onPostExecute(Void result) {

        }

        public String getJSON(String url, int timeout) {
            HttpsURLConnection c = null;
            try {
                java.net.URL u = new java.net.URL(url);
                c = (HttpsURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(timeout);
                c.setReadTimeout(timeout);
                c.connect();
                int status = c.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter) {
            Fragment filterFragment = new FilterFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, filterFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
