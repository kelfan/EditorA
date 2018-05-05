package com.kelfan.editfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BlockItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public BlockItemViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.block_item_textview);
    }
}
