package com.example.levelfiler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.StringSplit;
import com.kelfan.utillibrary.StringWorker;


public class LevelItemRecyclerAdapter extends RecyclerView.Adapter<LevelItemViewHolder> {

    String text;
    StringSplit data;
    Context context;
    LayoutInflater inflater;
    String delimiter="\n# ";
    String titleSign ="/";

    public static LevelItemRecyclerAdapter set(Context context, String text){
        return new LevelItemRecyclerAdapter().withContext(context).withText(text);
    }

    public LevelItemRecyclerAdapter withContext(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        return this;
    }

    public LevelItemRecyclerAdapter withText(String text){
        this.text = text;
        this.data = StringSplit.set(text).withDeilimiter(delimiter);
        return this;
    }

    @Override
    public LevelItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new LevelItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LevelItemViewHolder holder, int position) {
        String txt = data.get(position);
        holder.recyclerView.setBackgroundColor(ColorWorker.strToColor(txt));

        // process title
        String title = StringWorker.beginToLastSign(txt, titleSign);
        if (title.equals("")){
            title = "others";
        }
        holder.textView.setText(title);

        // process recycler view
        if (holder.recyclerView.getAdapter() == null){
            LevelSubRecyclerAdapter levelSubRecyclerAdapter = LevelSubRecyclerAdapter.set(holder.itemView.getContext(), txt);
            holder.recyclerView.setAdapter(levelSubRecyclerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public int getItemCount() {
        return data.sizeFiltered();
    }
}
