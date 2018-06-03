package com.example.levelfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LevelSubViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public LevelSubViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.item_content_text_view);
    }
}
