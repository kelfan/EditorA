package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.RegexWorker;
import com.kelfan.utillibrary.StringHashList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;


public class ItemPresetAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnClickListener {

    String ALL_ITEMS = "All/";
    String OTHER_ITEMS = "Others/";

    String text;
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    String delimiter = "\n\n# ";
    String[] presetList = {};
    private ItemPresetAdapter.OnItemClickListener onItemClickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static ItemPresetAdapter set(Context context, String text) {
        return new ItemPresetAdapter().withContext(context).withText(text).doTitleList();
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

    public ItemPresetAdapter doStyle() {
        this.presetList = new String[]{"进行", "最近", "计划", "等待", "周期", "购物", "待做", "项目", "全部", ALL_ITEMS};
        return this;
    }

    public ItemPresetAdapter doTitleList() {
        if (this.presetList.length == 0) {
            Set<String> l = RegexWorker.matchAllSet(text, "# (.*)/");
            l.add(ALL_ITEMS);
            this.presetList = l.toArray(new String[l.size()]);
        }
        return this;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view, parent, false);
        view.setOnClickListener(this);
        Hub.linkedHashMap.put(Hub.mainData, this.data);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        String title = presetList[position].replace("# ", "");
        holder.textView.setText(title);
        holder.itemView.setTag(position);

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
        SubRecyclerAdapter levelSubRecyclerAdapter;
        StringHashList itemData;
        if (title.equals(ALL_ITEMS)) {
            itemData = data;
        } else {
            itemData = data.subContain(title);
        }
        if (holder.recyclerView.getAdapter() == null) {
            levelSubRecyclerAdapter = SubRecyclerAdapter.set(holder.itemView.getContext(), itemData);
            levelSubRecyclerAdapter.style = AtSign.set(text, "style").getValue();
            holder.recyclerView.setAdapter(levelSubRecyclerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            levelSubRecyclerAdapter = (SubRecyclerAdapter) holder.recyclerView.getAdapter();
            levelSubRecyclerAdapter.data = itemData;
            levelSubRecyclerAdapter.notifyDataSetChanged();
        }
        if (levelSubRecyclerAdapter.data.size() == 0) {
            holder.textView.setVisibility(View.GONE);
        } else {
            holder.textView.setVisibility(View.VISIBLE);
        }
        if (!levelSubRecyclerAdapter.hasTouchHelper) {
            TouchHelper.newHelper(holder.recyclerView, levelSubRecyclerAdapter);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return presetList.length;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取position
            onItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
