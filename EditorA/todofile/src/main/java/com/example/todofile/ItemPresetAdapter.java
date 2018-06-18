package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.StringSplit;
import com.kelfan.utillibrary.StringWorker;

import java.net.Authenticator;


public class ItemPresetAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnClickListener {

    String text;
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    String delimiter = "\n\n# ";
    String[] presetList = {"进行", "最近", "计划", "等待", "周期", "购物", "待做", "项目", "全部"};
    private ItemPresetAdapter.OnItemClickListener onItemClickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

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
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String title = presetList[position];
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
        if (holder.recyclerView.getAdapter() == null) {
            if (title.equals("全部")) {
                levelSubRecyclerAdapter = SubRecyclerAdapter.set(holder.itemView.getContext(), data);
            } else {
                levelSubRecyclerAdapter = SubRecyclerAdapter.set(holder.itemView.getContext(), data.subContain(title + "/"));
            }
            levelSubRecyclerAdapter.style = AtSign.set(text, "style").getValue();
            holder.recyclerView.setAdapter(levelSubRecyclerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            levelSubRecyclerAdapter = (SubRecyclerAdapter) holder.recyclerView.getAdapter();
            if (title.equals("全部")) {
                levelSubRecyclerAdapter.data = data;
            } else {
                levelSubRecyclerAdapter.data = data.subContain(title + "/");
            }

            levelSubRecyclerAdapter.notifyDataSetChanged();
        }
        if (levelSubRecyclerAdapter.data.size() == 0) {
            holder.textView.setVisibility(View.GONE);
        } else {
            holder.textView.setVisibility(View.VISIBLE);
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
