package view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import cpbr11.campuseromobile.R;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import model.Activity;
import view.HeaderRecyclerViewSection;

public class AgendaListFragment extends Fragment {

    private List<Activity> activities;
    private List<String> hours;
    SectionedRecyclerViewAdapter sectionAdapter;

    public AgendaListFragment() {

    }

    @SuppressLint("ValidFragment")
    public AgendaListFragment(List<Activity> activities, List<String> hours) {
        this.activities = activities;
        this.hours = hours;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_agenda, container, false);
        
        RecyclerView sectionHeader = (RecyclerView) rootView.findViewById(R.id.add_header);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        sectionHeader.setLayoutManager(linearLayoutManager);
        sectionHeader.setHasFixedSize(true);
        sectionAdapter = new SectionedRecyclerViewAdapter();

        for(String hour : hours){
            List<Activity> hourActivities = new ArrayList<>();

            for (Activity a : activities) {
                String hourFirst = a.getStartDate().split("T")[1].split(":")[0];
                String visualHourFirst = hour.split(":")[0];

                if (hourFirst.equals(visualHourFirst)) {
                    hourActivities.add(a);
                }
            }

            if(!hourActivities.isEmpty()) {
                HeaderRecyclerViewSection section = new HeaderRecyclerViewSection(hour, hourActivities);
                sectionAdapter.addSection(section);
            }
        }

        sectionHeader.setAdapter(sectionAdapter);

        return rootView;
    }
}