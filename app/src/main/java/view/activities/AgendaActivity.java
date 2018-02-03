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
import java.util.List;
import java.util.concurrent.ExecutionException;

import cpbr11.campuseromobile.R;
import model.Activity;
import request.ActvivityRequest;
import view.SectionsPagerAdapter;
import view.fragments.FilterFragment;
import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class AgendaActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agenda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        ArrayList<ArrayList<Activity>> allDays = new ArrayList<>();
        List<Activity> allActivities = null;

        try {

            allActivities = new ActvivityRequest(this).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        List<String> dates = new ArrayList<>();

        String dayOld = null;
        int indexSave = -1;

        for(Activity act : allActivities){
            String dayAct = act.getStartDate().split("T")[0];
            if(!dayAct.equals(dayOld)){
                dates.add(dayAct);
                Log.d("DATE",dayAct);
                allDays.add(new ArrayList<Activity>());
                indexSave++;
                dayOld = dayAct;
            }
            allDays.get(indexSave).add(act);

        }

        Log.d("List", allDays.toString());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), allDays,
                dates, allDays.size());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);*/

        List<model.Activity> day1_activities = new ArrayList<>();
        model.Activity a1 = new model.Activity("Teste 1", "10:00", "11:00", "Stage");
        model.Activity a2 = new model.Activity("Teste 1", "11:00", "12:00", "Stage");
        model.Activity a3 = new model.Activity("Teste 1", "12:00", "13:00", "Stage");
        day1_activities.add(a1);
        day1_activities.add(a2);
        day1_activities.add(a3);
        List<model.Activity> day2_activities = new ArrayList<>();
        model.Activity b1 = new model.Activity("Teste 2", "10:00", "11:00", "Stage");
        model.Activity b2 = new model.Activity("Teste 2", "11:00", "12:00", "Stage");
        model.Activity b3 = new model.Activity("Teste 2", "12:00", "13:00", "Stage");
        day2_activities.add(b1);
        day2_activities.add(b2);
        day2_activities.add(b3);
        List<List<model.Activity>> all_activities = new ArrayList<>();
        all_activities.add(day1_activities);
        all_activities.add(day2_activities);
        List<String> dates = new ArrayList<>();
        dates.add("01/02");
        dates.add("02/02");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), all_activities,
                dates, all_activities.size());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
        tabs.setTabMode(MODE_SCROLLABLE);
        tabs.setupWithViewPager(mViewPager);

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
