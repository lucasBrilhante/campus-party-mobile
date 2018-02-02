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

import java.util.ArrayList;
import java.util.List;

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

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://sandboxapi.campuse.ro/agenda/list/campus-party-brasil-2018/";

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
        JsonObjectRequest activityRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Log.d("DEU BOOOOOOOOOOOOOOOOM ",response.toString());


                        try {
                            int count = response.getInt("count");


                            for(int i = 0;i<count;i++) {
                                JSONObject activityJson = response.getJSONArray("results").getJSONObject(i);
                                Activity activity = new Activity(activityJson.getString("slug"),activityJson.getString("start_date"),activityJson.getString("end_date"),activityJson.getString("get_type"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sectionAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EROOOOOOOOOOOOOOOOOR","DEU RUIM");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(activityRequest);




        return rootView;
    }
}