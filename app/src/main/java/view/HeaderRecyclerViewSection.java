package view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.concurrent.ExecutionException;

import Presenter.ActivityPresenter;
import cpbr11.campuseromobile.R;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import model.Activity;
import view.fragments.EventFragment;

public class HeaderRecyclerViewSection extends StatelessSection{

    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    private String title;
    private List<Activity> list;
    private Context context;
    private boolean isPersonalAgenda;

    public HeaderRecyclerViewSection(String title, List<Activity> list, Context context, Boolean isPersonalAgenda) {

        super(R.layout.header_layout, R.layout.item_layout);
        this.title = title;
        this.list = list;
        this.context = context;
        this.isPersonalAgenda = isPersonalAgenda;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder iHolder = (ItemViewHolder) holder;
        Activity currentActivity = list.get(position);
        iHolder.activityName.setText(currentActivity.getName().split("#")[0]);
        String start = currentActivity.getStartDate().split("T")[1].split("Z")[0];
        String end = currentActivity.getEndDate().split("T")[1].split("Z")[0];
        iHolder.activityInfo.setText(start.substring(0, start.length() - 3) + " - " +
                end.substring(0, end.length() - 3) + ", " +
                currentActivity.getStage().split("cpbr11")[0].split("#CPBR11")[0]);
        if (isPersonalAgenda){
            iHolder.icon.setImageResource(R.drawable.ic_remove_act);
        }else{
            Activity activity = list.get(position);
            try {
                if(ActivityPresenter.getInstance().verifyActivity(activity, context)){
                    iHolder.icon.setImageResource(R.drawable.ic_added_act);
                } else {
                    iHolder.icon.setImageResource(R.drawable.calendar_plus);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        iHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = list.get(position);

                try {
                    if(!isPersonalAgenda) {
                        ActivityPresenter.getInstance().insertActivies(context, activity);
                        iHolder.icon.setImageResource(R.drawable.ic_added_act);

                        CharSequence text = "Adicionado com sucesso!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }else {
                        ActivityPresenter.getInstance().removeActivity(context, activity);
                        list.remove(position);

                        CharSequence text = "Removido com sucesso!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //iHolder.icon.setColorFilter(000000);
            }
        });

        iHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = list.get(position);
                Fragment fragment = new EventFragment(activity);


                AppCompatActivity act = (AppCompatActivity)context;

                FragmentManager fragmentManager = act.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder hHolder = (HeaderViewHolder)holder;
        hHolder.headerTitle.setText(title);
    }
}