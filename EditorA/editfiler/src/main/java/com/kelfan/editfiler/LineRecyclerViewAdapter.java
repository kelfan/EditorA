package com.kelfan.editfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.ListString;
import com.kelfan.utillibrary.StringLocal;
import com.kelfan.utillibrary.Xmler;

import java.lang.reflect.Field;

/**
 * Process -> set text content -> get patterns [listPattern, contentPattern, titlePattern, subPattern]
 * -> getDataList
 */
public class LineRecyclerViewAdapter extends RecyclerView.Adapter<LineItemViewHolder> implements View.OnClickListener {
    private LayoutInflater inflater;
    private String textContent;
    private ListString dataList;
    private Context context;
    //   public String listPattern = "[\n\r]*(.+)[\n\r]*";
    public String listPattern = "";
    public String contentPattern = ".*";
    public String titlePattern = "";
    public String subPattern = "";
    public String scopePattern = "";
    public String delimiter = "\n";
    public String adapter = "";
    public String style = "";
    public String recordTime = "False";
    public String updateTime = "False";
    public String archive = "false";

    public final static String recordTag = "record_time";
    public final static String updateTag = "update_time";
    private LineRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public LineRecyclerViewAdapter removeItem(int position) {
        this.dataList.remove(position);
        return this;
    }

    public LineRecyclerViewAdapter(Context context, String text) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        setContent(text).getPatterns().setDataList();
    }

    public void sort() {
        this.dataList.sortItem();
    }

    private LineRecyclerViewAdapter setContent(String inStr) {
        this.textContent = inStr;
        return this;
    }

    public String getText(int pos) {
        return this.dataList.get(pos);
    }

    public void addItem(String text) {
        this.dataList.add(text);
    }

    public void setData(String text, int position) {
        dataList.set(position, text);
    }

    private LineRecyclerViewAdapter setDataList() {
        Xmler x = Xmler.set(this.textContent, "delimiter");
        Xmler x2 = Xmler.set(this.textContent, "listPattern");
        if (!x.getContent().equals("")) {
//            String s = String.format("([^%s]+)", delimiter, delimiter);
            this.dataList = ListString.set(x.getRemain()).setDelimiter(delimiter).getSplitList();
            this.dataList.add(x.setXmlContent());
        } else if (!x2.getContent().equals("")) {
            this.dataList = ListString.set(x2.getRemain()).getPatternList(this.listPattern);
            this.dataList.add(x2.setXmlContent());
        } else if (this.delimiter.equals("")) {
            this.dataList = ListString.set(this.textContent).getPatternList(this.listPattern);
        } else {
            this.dataList = ListString.set(this.textContent).setDelimiter(delimiter).getSplitList();
        }
        return this;
    }

    private LineRecyclerViewAdapter getPatterns() {
        getPattern("style");
        processStyle();
        getPattern("adapter");
        getPattern("delimiter");
        getPattern("listPattern");
        getPattern("contentPattern");
        getPattern("titlePattern");
        getPattern("subPattern");
        getPattern("recordTime");
        getPattern("updateTime");
        getPattern("archive");
        return this;
    }

    private LineRecyclerViewAdapter processStyle() {
        if (this.style.equals("block")) {
            this.titlePattern = ".+";
            this.adapter = "block";
            this.subPattern = "[~~][\\s\\S]*";
            this.delimiter = "\n\n\n";
        } else if (this.style.equals("line")) {
            this.titlePattern = "[^:：]+?[:：]";
            this.subPattern = "[~~][\\s\\S]*";
            this.delimiter = "\n";
        } else if (this.style.equals("log")) {
            this.titlePattern = "[^:：]+?[:：]";
            this.subPattern = "[~~][\\s\\S]*";
            this.delimiter = "\n";
            this.recordTime = "true";
            this.updateTime = "true";
            this.archive = "true";
        } else if(this.style.equals("md")){
            this.titlePattern = ".+";
            this.adapter = "block";
            this.subPattern = "[~~][\\s\\S]*";
            this.delimiter = "\n\n#";
        }else if (this.style.equals("todo")){
            this.titlePattern = "[^:：]+?[:：]";
            this.subPattern = "[~~][\\s\\S]*";
            this.delimiter = "\n";
            this.recordTime = "true";
            this.updateTime = "true";
            this.archive = "true";
        }
        return this;
    }

    private LineRecyclerViewAdapter getPattern(String patternName) {
        String tmp = Xmler.set(textContent, patternName).getContent();
        if (!tmp.equals("")) {
            try {
                Field filed = this.getClass().getField(patternName);
                filed.set(this, tmp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    ListString getDataList() {
        return this.dataList;
    }

    @Override
    public LineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (adapter.equals("block")) {
            view = inflater.inflate(R.layout.block_item_view, parent, false);
        } else {
            view = inflater.inflate(R.layout.line_item_view, parent, false);
        }
        view.setOnClickListener(this);
        return new LineItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineItemViewHolder holder, int position) {
        StringLocal s = dataList.getItem(position).getRemain("<"+recordTag+">.*</"+recordTag+">").getRemain("<"+updateTag+">.*</"+updateTag+">");
        String title = s.getPattern(titlePattern).toString();
        setTextView(holder.titleTextView, title);
        setTextView(holder.subContentTextView, s.getPattern(subPattern).toString());
        holder.titleTextView.setBackgroundColor(
                ColorWorker.strToColor(holder.titleTextView.getText().toString())
        );
        holder.contentTextView.setText(s.getRemain(titlePattern).getRemain(subPattern).getRemain(scopePattern).toString());
        holder.itemView.setTag(position);
    }

    private void setTextView(TextView view, String text) {
        if (text.equals("")) {
            view.setVisibility(View.GONE);
        } else {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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

    public String getItem(int position){
        return this.dataList.get(position);
    }

}
