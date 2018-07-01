package com.example.todofile;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.RegexWorker;
import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.TimeWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ItemPresetAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnClickListener {

    String ALL_ITEMS = "All/";
    String RECENT_ITEMS = "Recent/";
    String OTHER_ITEMS = "Others/";

    String text;
    StringHashList data;
    Context context;
    LayoutInflater inflater;
    String delimiter = "\n\n# ";
    String[] presetList = {};
    private ItemPresetAdapter.OnItemClickListener onItemClickListener;

    String style = "";
    boolean isLog = false;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static ItemPresetAdapter set(Context context, String text) {
        if (text.length() > 1) {
            text = text.substring(0, text.length() - 1);
        }
        return new ItemPresetAdapter().withContext(context).withText(text).doStyle().doList().doTitleList();
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

    public ItemPresetAdapter doTitleList() {
        if (this.presetList.length == 0) {
            Set<String> l = RegexWorker.matchAllSet(text, "# (.*)/");
            l.add(ALL_ITEMS);
            String[] out;
            if (this.style.equals("todo")) {
                out = new String[l.size() + 1];
                out[0] = RECENT_ITEMS;
                int counter = 1;
                for (String s : l) {
                    out[counter] = s;
                    counter++;
                }
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
        } else if (title.equals(RECENT_ITEMS)) {
            StringHashList stringHashList = new StringHashList();
            for (Long key : data.getValues().keySet()) {
                String s = data.get(key);
                if (s.contains("@date_")) {
                    String sDate = AtSign.set(s, "date").getValue();
                    Date date = TimeWorker.parseDate(sDate, Replacer.TO_DATE);
                    if (date != null) {
                        Long days = TimeWorker.difToday(date);
                        Log.e("fan", days.toString());
                        if (days >-1 && days < 14){
                            stringHashList.put(key, data.get(key));
                        }
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
