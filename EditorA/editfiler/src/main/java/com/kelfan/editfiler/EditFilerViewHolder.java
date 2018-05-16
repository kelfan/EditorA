package com.kelfan.editfiler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditFilerViewHolder extends RecyclerView.ViewHolder {

    EditText editText;
    ImageButton imageButton;

    public EditFilerViewHolder(View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.editfiler_fragment_edit_text);
        imageButton = itemView.findViewById(R.id.editfiler_fragment_image_button);
    }
}
