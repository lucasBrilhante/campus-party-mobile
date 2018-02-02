package view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import model.Activity;
import view.fragments.AgendaListFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List< List<Activity> > activities;
    private List<String> dates;
    private int numTabs;

    public SectionsPagerAdapter(FragmentManager fm,
                                List<List<Activity>> activities, List<String> dates,
                                int numTabs) {
        super(fm);
        this.activities = activities;
        this.dates = dates;
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return new AgendaListFragment(activities.get(position))  ;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String date = dates.get(position);
        return date;
    }
}