package com.example.todofile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SubViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    TextView titleView;

    public SubViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.item_content_text_view);
        titleView = itemView.findViewById(R.id.item_title_view);
    }
}
