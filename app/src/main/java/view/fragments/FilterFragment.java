package view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import cpbr11.campuseromobile.R;

public class FilterFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        ListView categoryList = (ListView) rootView.findViewById(R.id.categoryList);
        ListView stageList = (ListView) rootView.findViewById(R.id.stageList);

        List<String> categories = new ArrayList<String>();
        categories.add("Palestras");
        categories.add("Workshops");
        categories.add("Desafios");

        List<String> stages = new ArrayList<>();
        stages.add("Palco Creativity");
        stages.add("Palco Games");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(),
                        android.R.layout.simple_list_item_multiple_choice,
                        android.R.id.text1, categories);

        categoryList.setAdapter(adapter);
        stageList.setAdapter(adapter);

        return rootView;
    }
}
