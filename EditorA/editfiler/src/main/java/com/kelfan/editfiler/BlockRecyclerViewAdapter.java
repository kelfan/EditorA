package com.kelfan.editfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.ListString;
import com.kelfan.utillibrary.StringLocal;

import java.util.List;

public class BlockRecyclerViewAdapter extends RecyclerView.Adapter<BlockItemViewHolder> {
    private LayoutInflater fInflater;
    private String text;
    private ListString blockList;
    private Context context;

    public BlockRecyclerViewAdapter setText(String text) {
        this.text = text;
        this.blockList = ListString.set(text).getPatternList("[#]*[^#]+");
        return this;
    }

    public BlockRecyclerViewAdapter(Context context, String text) {
        this.context = context;
        setText(text);
        fInflater = LayoutInflater.from(context);
    }

    @Override
    public BlockItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = fInflater.inflate(R.layout.block_item_view, parent, false);
        return new BlockItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BlockItemViewHolder holder, int position) {
        holder.itemTextView.setTag(position);
        StringLocal curContent = blockList.getItem(position);
        int titleLevel = curContent.countLetter("#");
        StringLocal tmp = curContent;
        if (titleLevel>0){
            String test = curContent.getPattern("[#]*[^\n]*").toString().replaceAll("#", "");
            holder.titleTextView.setText(curContent.getPattern("[#]*[^\n]*").toString().replaceAll("#", ""));
            tmp = curContent.getRemain("[#]*[^\n]+[\n]*");
            holder.titleTextView.setBackgroundColor(ColorWorker.pickColor(titleLevel));
            holder.titleTextView.setTextSize(titleLevel * 5 + 10);
            holder.titleTextView.setVisibility(View.VISIBLE);
        }else {
            holder.titleTextView.setVisibility(View.GONE);
        }

        holder.itemTextView.setText(tmp.toString());
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }
}
