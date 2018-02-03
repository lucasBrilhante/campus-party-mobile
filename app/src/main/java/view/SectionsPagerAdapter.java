package view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.Activity;
import view.fragments.AgendaListFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Object[] activities;
    private Object[] dates;
    private int numTabs;

    public SectionsPagerAdapter(FragmentManager fm,
                                HashMap<String, List<Activity>> activities) {
        super(fm);
        this.activities = activities.values().toArray();
        this.numTabs = activities.size();
        this.dates = activities.keySet().toArray();
    }

    @Override
    public Fragment getItem(int position) {
        List<String> hours = new ArrayList<>();

        for(int i=0;i<24;i++){
            String s = String.valueOf(i);
            if (i < 10)
                s = "0" + s;
            hours.add(s + ":00");
        }

        return new AgendaListFragment((List<Activity>) activities[position], hours) ;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String date = dates[position].toString();
        return date;
    }
}