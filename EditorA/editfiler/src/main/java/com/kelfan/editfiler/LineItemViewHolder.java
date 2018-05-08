package com.kelfan.editfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LineItemViewHolder extends RecyclerView.ViewHolder {

    TextView contentTextView;
    TextView titleTextView;
    TextView subContentTextView;

    public LineItemViewHolder(View itemView) {
        super(itemView);
        contentTextView = itemView.findViewById(R.id.line_item_content_text_view);
        titleTextView = itemView.findViewById(R.id.line_item_title_view);
        subContentTextView = itemView.findViewById(R.id.line_item_subcontent_textview);

    }
}
