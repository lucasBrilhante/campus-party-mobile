package view.activities;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cpbr11.campuseromobile.R;
import view.SectionsPagerAdapter;

public class AgendaActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        List<model.Activity> day1_activities = new ArrayList<>();
        model.Activity a1 = new model.Activity("Teste 1", "10:00", "11:00");
        model.Activity a2 = new model.Activity("Teste 1", "11:00", "12:00");
        model.Activity a3 = new model.Activity("Teste 1", "12:00", "13:00");
        day1_activities.add(a1);
        day1_activities.add(a2);
        day1_activities.add(a3);
        List<model.Activity> day2_activities = new ArrayList<>();
        model.Activity b1 = new model.Activity("Teste 2", "10:00", "11:00");
        model.Activity b2 = new model.Activity("Teste 2", "11:00", "12:00");
        model.Activity b3 = new model.Activity("Teste 2", "12:00", "13:00");
        day2_activities.add(b1);
        day2_activities.add(b2);
        day2_activities.add(b3);
        List<List<model.Activity>> all_activities = new ArrayList<>();
        all_activities.add(day1_activities);
        all_activities.add(day2_activities);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), all_activities);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
