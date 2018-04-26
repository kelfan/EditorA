package com.kelfan.logfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelfan.utillibrary.ColorWorker;

import java.util.ArrayList;
import java.util.Collections;

public class LogFilerRecyclerViewAdapter extends RecyclerView.Adapter<LogFilerItemViewHolder> {

    private LayoutInflater fInflater;
    private Context fContext;
    private ArrayList<String> fData;
    private EditText editText;

    public LogFilerRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.fContext = context;
        Collections.reverse(data);
        this.fData = data;
        fInflater = LayoutInflater.from(context);
    }

    public String getNewItem(LogFilerItemViewHolder holder) {
        return holder.editText.getText().toString();
    }

    public void setData(ArrayList<String> data) {
        Collections.reverse(data);
        this.fData = data;
        notifyDataSetChanged();
    }

    @Override
    public LogFilerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = fInflater.inflate(R.layout.log_item_view, parent, false);
        LogFilerItemViewHolder logFilerItemViewHolder = new LogFilerItemViewHolder(view);
        return logFilerItemViewHolder;
    }

    @Override
    public void onBindViewHolder(LogFilerItemViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.textView.setTag(position);
        String line = fData.get(position);

        String mainContent = line;
        if (line.contains(",")) {
            mainContent = line.substring(line.indexOf(",") + 1);
        }
        if (mainContent.contains(":") | mainContent.contains("：")) {
            int pos;
            if (mainContent.contains(":")){
                pos = mainContent.indexOf(":");
            }else{
                pos = mainContent.indexOf("：");
            }
            String title = mainContent.substring(0, pos);
            mainContent = mainContent.substring(pos + 1);

            holder.titleView.setText(title);
            holder.titleView.setBackgroundColor(ColorWorker.strToColor(title));
        }
        holder.textView.setText(mainContent);
    }

    @Override
    public int getItemCount() {
        return fData.size();
    }
}
