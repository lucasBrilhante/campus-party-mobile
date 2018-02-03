package view.activities;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Presenter.ActivityPresenter;
import cpbr11.campuseromobile.R;
import dao.AppDatabase;
import model.Activity;
import request.ActvivityRequest;
import view.SectionsPagerAdapter;
import view.fragments.FilterFragment;
import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class AgendaActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static HashMap<String, List<Activity>> activitiesByDay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agenda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        boolean isPersonalAgenda = false; // or other values
        if(b != null)
            isPersonalAgenda = b.getBoolean("AgendaPessoal");

        if (!isPersonalAgenda) {


            if(activitiesByDay == null) {
                List<Activity> allActivities = null;

                try {

                    allActivities = new ActvivityRequest(this).execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                activitiesByDay = new HashMap<>();
                List<String> hours = new ArrayList<>();

                for (Activity a : allActivities) {
                    String[] parsed = a.getStartDate().split("T");
                    String daytmp = parsed[0];
                    String hour = parsed[1];

                    String day = daytmp.split("-")[2] + "/" + daytmp.split("-")[1];

                    if (hours.indexOf(hour) == -1) {
                        hours.add(hour);
                    }

                    if (!activitiesByDay.containsKey(day)) {
                        activitiesByDay.put(day, new ArrayList<Activity>());
                    }
                    activitiesByDay.get(day).add(a);
                }
            }

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), activitiesByDay);
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
            tabs.setTabMode(MODE_SCROLLABLE);
            tabs.setupWithViewPager(mViewPager);
        }else{
            List<Activity> allActivities = null;

            try {
                allActivities = ActivityPresenter.getInstance().getActivities(getApplicationContext());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Comparator<Activity> cmp = new Comparator<Activity>() {
                @Override
                public int compare(Activity activity1, Activity activity2) {
                    return activity1.getStartDate().compareTo(activity2.getStartDate());
                }
            };

            Collections.sort(allActivities, cmp);

            HashMap<String, List<Activity>> activitiesByDayPersonal = null;

            activitiesByDayPersonal = new HashMap<>();
            List<String> hours = new ArrayList<>();

            for (Activity a : allActivities) {
                String[] parsed = a.getStartDate().split("T");
                String daytmp = parsed[0];
                String hour = parsed[1];

                String day = daytmp.split("-")[2] + "/" + daytmp.split("-")[1];

                if (hours.indexOf(hour) == -1) {
                    hours.add(hour);
                }

                if (!activitiesByDayPersonal.containsKey(day)) {
                    activitiesByDayPersonal.put(day, new ArrayList<Activity>());
                }
                activitiesByDayPersonal.get(day).add(a);
            }


            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), activitiesByDayPersonal);
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
            tabs.setTabMode(MODE_SCROLLABLE);
            tabs.setupWithViewPager(mViewPager);

        }

        super.onCreate(savedInstanceState);
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
