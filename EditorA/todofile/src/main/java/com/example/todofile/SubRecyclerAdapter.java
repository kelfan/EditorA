package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.RegexWorker;
import com.kelfan.utillibrary.StringHashList;
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
    String style = "";
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
            id = R.layout.item_view_sub_block_note;
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
            AtSign create = AtSign.set(text, "create");
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
            } else if (!create.getValue().equals("")) {
                date = TimeWorker.parseDate(create.getValue(), Replacer.DATE_TIME_FORMAT);
            }
            if (date != null) {
                title = TimeWorker.formatDate("MM-dd EEE", date);
            }
            text = atSign.getRemain();
        }

        String[] dropList = new String[]{"create", "modify"};
        for (String s : dropList) {
            AtSign value = AtSign.set(text, s);
            if (!value.getValue().equals("")) {
                text = value.getRemain();
            }
        }

        if (!titleSeparator.equals("")) {
            List<String> match = RegexWorker.matchAll(text, "^.*?" + titleSeparator);

            if (match.size() > 0) {
                title = match.get(0);
                text = text.replace(title, "");
                if (title.length() > 0) {
                    title = title.substring(0, title.length() - 1);
                }
            } else if (style.equals("block")) {
                title = text;
                text = "";
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

        if (text.equals("")) {
            holder.textView.setVisibility(View.GONE);
        } else {
            holder.textView.setText(text);
            holder.textView.setVisibility(View.VISIBLE);
        }

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
