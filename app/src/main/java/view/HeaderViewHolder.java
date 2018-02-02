package view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import cpbr11.campuseromobile.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView headerTitle;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView)itemView.findViewById(R.id.header_id);
    }
}