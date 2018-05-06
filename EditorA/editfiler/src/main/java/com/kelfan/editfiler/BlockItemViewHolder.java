package com.kelfan.editfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BlockItemViewHolder extends RecyclerView.ViewHolder {

    TextView itemTextView;
    TextView titleTextView;

    public BlockItemViewHolder(View itemView) {
        super(itemView);
        itemTextView = itemView.findViewById(R.id.block_item_textview);
        titleTextView = itemView.findViewById(R.id.block_item_title_textview);
    }
}
