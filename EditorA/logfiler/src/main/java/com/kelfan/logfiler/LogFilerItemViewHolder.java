package com.kelfan.logfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LogFilerItemViewHolder extends RecyclerView.ViewHolder {

    EditText editText;
    TextView textView;
    TextView titleView;

    LogFilerItemViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.log_item_text_view);
        editText = itemView.findViewById(R.id.log_edit_text);
        titleView = itemView.findViewById(R.id.log_item_title_view);
    }
}
