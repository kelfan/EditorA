package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.StringSplit;

public class SubRecyclerAdapter extends RecyclerView.Adapter<SubViewHolder> {
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    String style = "line";

    public static SubRecyclerAdapter set(Context context, StringHashList data) {
        return new SubRecyclerAdapter().withContext(context).withText(data);
    }

    public SubRecyclerAdapter withContext(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        return this;
    }

    public SubRecyclerAdapter withText(StringHashList data) {
        this.data = data;
        return this;
    }

    @Override
    public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id;
        if (style.equals("block")) {
            id = R.layout.item_view_sub_block;
        }else {
            id = R.layout.item_view_sub_line;
        }
        View view = inflater.inflate(id, parent, false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
