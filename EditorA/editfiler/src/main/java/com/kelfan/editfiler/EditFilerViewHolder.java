package com.kelfan.editfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EditFilerViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public EditFilerViewHolder(View itemView) {
        super(itemView);
        textView  = itemView.findViewById(R.id.editfiler_textview);
    }
}
