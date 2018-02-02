package view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cpbr11.campuseromobile.R;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import model.Activity;
import view.HeaderRecyclerViewSection;

public class AgendaListFragment extends Fragment {

    private List<Activity> activities;
    SectionedRecyclerViewAdapter sectionAdapter;
    public AgendaListFragment() {

    }

    public AgendaListFragment(List<Activity> activities) {
        this.activities = activities;

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

        List<String> hours = new ArrayList<>();
        hours.add("10:00");
        hours.add("11:00");
        hours.add("12:00");

        for(String hour : hours){
            HeaderRecyclerViewSection section = new HeaderRecyclerViewSection(hour, activities);
            sectionAdapter.addSection(section);
        }

        sectionHeader.setAdapter(sectionAdapter);

        // Request a string response from the provided URL.





        return rootView;
    }
}