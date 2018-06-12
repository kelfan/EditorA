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
    String[] presetList = {"进行", "最近", "计划", "等待", "周期", "购物", "待做", "项目", "全部"};
    String[] test = {"test"};

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
        String title = presetList[position];
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
            SubRecyclerAdapter levelSubRecyclerAdapter;
            if (title.equals("全部")) {
                levelSubRecyclerAdapter = SubRecyclerAdapter.set(holder.itemView.getContext(), data);
            } else {
                levelSubRecyclerAdapter = SubRecyclerAdapter.set(holder.itemView.getContext(), data.subContain(title + "/"));
            }
            holder.recyclerView.setAdapter(levelSubRecyclerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public int getItemCount() {
        return presetList.length;
    }
}
