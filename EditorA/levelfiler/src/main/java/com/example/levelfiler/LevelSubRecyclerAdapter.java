package com.example.levelfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.StringSplit;

public class LevelSubRecyclerAdapter extends RecyclerView.Adapter<LevelSubViewHolder> {
    String text;
    StringSplit data;
    Context context;
    LayoutInflater inflater;

    public static LevelSubRecyclerAdapter set(Context context, String text){
        return new LevelSubRecyclerAdapter().withContext(context).withText(text);
    }

    public LevelSubRecyclerAdapter withContext(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        return this;
    }

    public LevelSubRecyclerAdapter withText(String text){
        this.text = text;
        this.data = StringSplit.set(text).withDeilimiter(",");
        return this;
    }

    @Override
    public LevelSubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view_sub, parent, false);
        return new LevelSubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LevelSubViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.sizeFiltered();
    }

}
