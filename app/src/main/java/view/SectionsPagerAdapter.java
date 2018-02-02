package view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import model.Activity;
import view.fragments.AgendaListFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List< List<Activity> > activities;

    public SectionsPagerAdapter(FragmentManager fm,
                                List< List<Activity> > activities) {
        super(fm);
        this.activities = activities;
    }

    @Override
    public Fragment getItem(int position) {
        return new AgendaListFragment(activities.get(position))  ;
    }

    @Override
    public int getCount() {
        final int NUM_TABS = 2;
        return NUM_TABS;
    }
}