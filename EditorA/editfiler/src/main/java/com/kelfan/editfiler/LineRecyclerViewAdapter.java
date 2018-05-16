package com.kelfan.editfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelfan.utillibrary.ListString;
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
    private LineRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public LineRecyclerViewAdapter(Context context, String text) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        setContent(text).getPatterns().setDataList();
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
        if (this.delimiter.equals("")){
            this.dataList = ListString.set(this.textContent).getPatternList(this.listPattern);
        }else {
            String s  = String.format("([^%s]+)", delimiter, delimiter);
            this.dataList = ListString.set(this.textContent).getPatternList(s);
        }
        return this;
    }

    private LineRecyclerViewAdapter getPatterns() {
        acquireDelimiter();
        acquireListPattern();
        acquireContentPattern();
        acquireTitlePattern();
        acquireSubPattern();
        return this;
    }


    private LineRecyclerViewAdapter acquireListPattern() {
        getPattern("listPattern");
        return this;
    }

    private LineRecyclerViewAdapter acquireContentPattern() {
        getPattern("contentPattern");
        return this;
    }

    private LineRecyclerViewAdapter acquireTitlePattern() {
        getPattern("titlePattern");
        return this;
    }

    private LineRecyclerViewAdapter acquireSubPattern() {
        getPattern("subPattern");
        return this;
    }

    private LineRecyclerViewAdapter acquireDelimiter(){
        getPattern("delimiter");
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
        View view = inflater.inflate(R.layout.line_item_view, parent, false);
        view.setOnClickListener(this);
        return new LineItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineItemViewHolder holder, int position) {
        String s = dataList.get(position);
        String title = dataList.getItem(position).getPattern(titlePattern).toString();
        setTextView(holder.titleTextView, title);
        setTextView(holder.subContentTextView, dataList.getItem(position).getPattern(subPattern).toString());
        holder.contentTextView.setText(dataList.getItem(position).getRemain(titlePattern).getRemain(subPattern).getRemain(scopePattern).toString());
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
}
