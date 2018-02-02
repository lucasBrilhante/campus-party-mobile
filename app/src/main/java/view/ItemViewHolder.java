package view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cpbr11.campuseromobile.R;

public class ItemViewHolder extends RecyclerView.ViewHolder{

    public TextView activityName;
    public TextView activityInfo;

    public ItemViewHolder(View itemView) {
        super(itemView);
        activityName = (TextView) itemView.findViewById(R.id.name);
        activityInfo = (TextView) itemView.findViewById(R.id.info);
    }
}