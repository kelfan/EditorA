package com.kelfan.editfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.ListString;

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
        holder.textView.setTag(position);
        holder.textView.setText(blockList.get(position));
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }
}
