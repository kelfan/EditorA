package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.RegexWorker;
import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.TimeWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class ItemPresetAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnClickListener {

    String ALL_ITEMS = "All/";
    String RECENT_ITEMS = "Recent/";
    String REPEAT_ITEMS = "Repeat/";
    String OTHER_ITEMS = "Others/";

    String text;
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    String delimiter = "\n\n# ";
    String[] presetList = {};
    private ItemPresetAdapter.OnItemClickListener onItemClickListener;

    String style = "";
    String titleSeparator = "";
    String subSeparator = "";
    String display = "";
    boolean logDelete = false;
    boolean isLog = false;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static ItemPresetAdapter set(Context context, String text) {
        if (text.length() > 1) {
            text = text.substring(0, text.length() - 1);
        }
        return new ItemPresetAdapter().withContext(context).withText(text).doStyle().doList().doTitleList().doTitleSeparator().doSubSeparator().doDisplay();
    }

    public ItemPresetAdapter withContext(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        return this;
    }

    public ItemPresetAdapter withText(String text) {
        this.text = text;
        if (text.equals("")) {
            this.data = new StringHashList();
        } else {
            String[] aList = text.split(delimiter);
            List<String> list = new ArrayList<String>(Arrays.asList(aList));
            if (list.contains("")) {
                list.removeAll(Arrays.asList("", null));
                aList = list.toArray(new String[0]);
            }
            this.data = StringHashList.set(aList);
        }
        return this;
    }

    public ItemPresetAdapter doStyle() {
        AtSign atSign = AtSign.set(text, "style");
        style = atSign.getValue();
        if (style.equals("list")) {
            titleSeparator = "[:：]";
            subSeparator = "\n";
        }
        if (style.equals("todo")) {
            logDelete = true;
            titleSeparator = "[:：]";
            subSeparator = "\n";
        }
        if (style.equals("log")) {
            logDelete = true;
            isLog = true;
            titleSeparator = "[:：]";
            subSeparator = "\n";
        }
        if (style.equals("block")) {
            titleSeparator = "\n";
            subSeparator = "~";
            this.display = "block";
        }
        if (style.equals("note")) {
            titleSeparator = "\n";
            subSeparator = "~";
            this.display = "block";
        }
        return this;
    }

    public ItemPresetAdapter doList() {
        AtSign atSign = AtSign.set(text, "list");
        if (!atSign.getValue().equals("")) {
            String text = atSign.getValue() + "_" + ALL_ITEMS;
            this.presetList = text.split("_");
        }
        return this;
    }

    ItemPresetAdapter doTitleSeparator() {
        String title = AtSign.set(text, "title").getValue();
        if (!title.equals("")) {
            this.titleSeparator = title;
        }
        return this;
    }

    ItemPresetAdapter doSubSeparator() {
        String sub = AtSign.set(text, "sub").getValue();
        if (!sub.equals("")) {
            this.subSeparator = sub;
        }
        return this;
    }

    ItemPresetAdapter doDisplay() {
        String display = AtSign.set(text, "display").getValue();
        if (!display.equals("")) {
            this.display = display;
        }
        return this;
    }

    public ItemPresetAdapter doTitleList() {
        if (this.presetList.length == 0) {
            Set<String> l = RegexWorker.matchAllSet(text, "# (.*)/");
            l.add(ALL_ITEMS);
            String[] out;
            if (this.style.equals("todo")) {
                out = new String[l.size() + 2];
                out[0] = RECENT_ITEMS;
                int counter = 1;
                for (String s : l) {
                    out[counter] = s;
                    counter++;
                }
                out[l.size()] = REPEAT_ITEMS;
                out[l.size() + 1] = ALL_ITEMS;
            } else {
                out = l.toArray(new String[l.size()]);
            }
            this.presetList = out;
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
        if (!title.contains("/")) {
            title += "/";
        }
        holder.textView.setText(title);
        holder.itemView.setTag(position);

        // process recycler view
        SubRecyclerAdapter levelSubRecyclerAdapter;
        StringHashList itemData;
        if (title.equals(ALL_ITEMS)) {
            itemData = data;
        } else if (title.equals(RECENT_ITEMS)) {
            StringHashList stringHashList = new StringHashList();
            for (Long key : data.getValues().keySet()) {
                String s = data.get(key);
                Date date = null;
                if (s.contains("@date_")) {
                    String sDate = AtSign.set(s, "date").getValue();
                    date = TimeWorker.parseDate(sDate, Replacer.TO_DATE);
                }
                if (s.contains("@lunar_")) {
                    date = StringParser.parseLunar(AtSign.set(s, "lunar").getValue());
                }
                if (s.contains("@repeaty_")) {
                    date = StringParser.parseRepeatY(AtSign.set(s, "repeaty").getValue());
                }
                if (date != null) {
                    Long days = TimeWorker.difToday(date);
                    if (days > -7 && days < 14) {
                        stringHashList.put(key, data.get(key));
                    }
                }

            }
            itemData = stringHashList;
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
        levelSubRecyclerAdapter.titleSeparator = this.titleSeparator;
        levelSubRecyclerAdapter.subSeparator = this.subSeparator;
        levelSubRecyclerAdapter.style = this.display;
        if (style.equals("todo")) {
            levelSubRecyclerAdapter.titleDate = true;
        }
        if (levelSubRecyclerAdapter.data.size() == 0) {
            holder.textView.setVisibility(View.GONE);
        } else {
            holder.textView.setVisibility(View.VISIBLE);
        }
        if (!levelSubRecyclerAdapter.hasTouchHelper) {
            TouchHelper.newHelper(holder.recyclerView, levelSubRecyclerAdapter);
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
