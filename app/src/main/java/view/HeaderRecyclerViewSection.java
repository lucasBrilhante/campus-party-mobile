package view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import cpbr11.campuseromobile.R;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import model.Activity;

public class HeaderRecyclerViewSection extends StatelessSection{

    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    private String title;
    private List<Activity> list;

    public HeaderRecyclerViewSection(String title, List<Activity> list) {
        super(R.layout.header_layout, R.layout.item_layout);
        this.title = title;
        this.list = list;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView activityName;
        public TextView activityInfo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            activityName = (TextView) itemView.findViewById(R.id.name);
            activityInfo = (TextView) itemView.findViewById(R.id.info);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Activity a = list.get(this.getAdapterPosition());
        }
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder iHolder = (ItemViewHolder) holder;
        Activity currentActivity = list.get(position);
        iHolder.activityName.setText(currentActivity.getName().split("#")[0]);
        String start = currentActivity.getStartDate().split("T")[1].split("Z")[0];
        String end = currentActivity.getEndDate().split("T")[1].split("Z")[0];
        iHolder.activityInfo.setText(start.substring(0,start.length() -3) + " - " +
                                     end.substring(0,end.length() -3) + ", " +
                                     currentActivity.getStage().split("cpbr11")[0].split("#CPBR11")[0]);
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