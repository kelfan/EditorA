package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.LunarSolarConverter;
import com.kelfan.utillibrary.RegexWorker;
import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.StringWorker;
import com.kelfan.utillibrary.TimeWorker;

import java.util.Date;
import java.util.List;


public class SubRecyclerAdapter extends RecyclerView.Adapter<SubViewHolder> implements View.OnClickListener {
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    boolean titleDate = false;
    String titleSeparator = "";
    String subSeparator = "";
    String style = "line";
    public boolean hasTouchHelper = false;

    private ItemPresetAdapter.OnItemClickListener onItemClickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

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
        } else {
            id = R.layout.item_view_sub_line;
        }
        View view = inflater.inflate(id, parent, false);
        view.setOnClickListener(this);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubViewHolder holder, final int position) {
        String text = data.get(position);
        text = RegexWorker.dropFirstMatch(text, ".*/");
        String title = "";
        String sub = "";

        if (titleDate) {
            AtSign atSign = AtSign.set(text, "date");
            AtSign lunar = AtSign.set(text, "lunar");
            AtSign repeatY = AtSign.set(text, "repeaty");
            title = atSign.getValue();
            Date date = null;
            if (!title.equals("")) {
                try {
                    date = TimeWorker.parseDate(atSign.getValue(), Replacer.TO_DATE);
                } catch (Exception e) {
                    title = title.substring(5, 10);
                }
            } else if (!lunar.getValue().equals("")) {
                date = StringParser.parseLunar(lunar.getValue());
            } else if (!repeatY.getValue().equals("")) {
                date = StringParser.parseRepeatY(repeatY.getValue());
            }
            if (date != null) {
                title = TimeWorker.formatDate("MM-dd EEE", date);
            }
            text = atSign.getRemain();
        }

        if (!titleSeparator.equals("")) {
            List<String> match = RegexWorker.matchAll(text, "^.*?" + titleSeparator);

            if (match.size() > 0) {
                title = match.get(0);
                text = text.replace(title, "");
            }
        }

        if (!subSeparator.equals("")) {
            List<String> match = RegexWorker.matchAll(text, "^.*?" + subSeparator);
            if (match.size() > 0) {
                String content = match.get(0);
                sub = text.replace(content, "");
                text = content.substring(0, content.length() - 1);
            }
        }

        if (title.equals("")) {
            holder.titleView.setVisibility(View.GONE);
        } else {
            holder.titleView.setText(title);
            holder.titleView.setVisibility(View.VISIBLE);
            holder.titleView.setBackgroundColor(ColorWorker.strToColor(title));
        }

        if (sub.equals("")) {
            holder.subView.setVisibility(View.GONE);
        } else {
            holder.subView.setText(sub);
            holder.subView.setVisibility(View.VISIBLE);
        }

        holder.textView.setText(text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoFragment todoFragment = (TodoFragment) Hub.linkedHashMap.get(Hub.mainFragment);
                todoFragment.editText.setText(data.get(position));
                todoFragment.currentItem = data.getKey(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取position
            onItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(ItemPresetAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
