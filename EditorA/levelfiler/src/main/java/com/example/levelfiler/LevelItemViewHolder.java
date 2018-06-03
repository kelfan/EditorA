package com.example.levelfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LevelItemViewHolder extends RecyclerView.ViewHolder{

    TextView textView;

    public LevelItemViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.item_text_view);
    }
}
