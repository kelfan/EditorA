package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.StringSplit;
import com.kelfan.utillibrary.StringWorker;


public class ItemPresetAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    String text;
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    String delimiter = "\n# ";
    String titleSign = "/";
    String[] presetList = {"working", "recent task", "schedule", "waiting", "period", "shopping", "todo", "project"};
    String[] test={"test"};

    public static ItemPresetAdapter set(Context context, String text) {
        return new ItemPresetAdapter().withContext(context).withText(text);
    }

    public ItemPresetAdapter withContext(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        return this;
    }

    public ItemPresetAdapter withText(String text) {
        this.text = text;
        String[] list = text.split(delimiter);
        this.data = StringHashList.set(list);
        return this;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String title = test[position];
        holder.textView.setText(title);

//        String txt = data.get(position);
//        holder.recyclerView.setBackgroundColor(ColorWorker.strToColor(txt));
//
//        // process title
//        String title = StringWorker.beginToLastSign(txt, titleSign);
//        if (title.equals("")) {
//            title = "preset list";
//        }
//        holder.textView.setText(title);
//
        // process recycler view
        if (holder.recyclerView.getAdapter() == null) {
            SubRecyclerAdapter levelSubRecyclerAdapter = SubRecyclerAdapter.set(holder.itemView.getContext(), data);
            holder.recyclerView.setAdapter(levelSubRecyclerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public int getItemCount() {
        return test.length;
    }
}
