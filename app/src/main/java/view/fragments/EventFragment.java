package view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cpbr11.campuseromobile.R;
import model.Activity;

public class EventFragment extends android.support.v4.app.Fragment {

    private Activity activity;

    public EventFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        TextView title = rootView.findViewById(R.id.event_title);
        title.setText(activity.getName());

        TextView date = rootView.findViewById(R.id.event_date);
        date.setText(mountDate(activity.getStartDate(), activity.getEndDate()));

        TextView stadium = rootView.findViewById(R.id.event_stadium);
        stadium.setText(activity.getStage().split("cpbr11")[0].split("#CPBR11")[0]);

        TextView description = rootView.findViewById(R.id.event_description);
        description.setText(activity.getDescription().split("<p>")[1].split("</p>")[0]);

        return rootView;
    }

    private String parseHour(String startDate, String endDate) {
        String start = startDate.split("T")[1].split("Z")[0];
        String end = endDate.split("T")[1].split("Z")[0];
        String date = start.substring(0,start.length() -3) + " - " +
                end.substring(0,end.length() -3);

        return date;
    }

    private String parseDay(String date) {
        String[] parsed = date.split("T");
        String dayTmp = parsed[0];

        String day = dayTmp.split("-")[2] + "/" + dayTmp.split("-")[1];

        return day;
    }

    private String mountDate(String startDate, String endDate) {
        return parseDay(startDate) + ", " + parseHour(startDate, endDate);
    }
}
