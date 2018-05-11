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
public class LineRecyclerViewAdapter extends RecyclerView.Adapter<LineItemViewHolder> {
    private LayoutInflater inflater;
    private String textContent;
    private ListString dataList;
    private Context context;
    public String listPattern = "[\n\r]*(.+)[\n\r]*";
    public String contentPattern = ".*";
    public String titlePattern = "";
    public String subPattern = "";
    public String scopePattern = "";

    public LineRecyclerViewAdapter(Context context, String text) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        setContent(text).getPatterns().setDataList();
    }

    private LineRecyclerViewAdapter setContent(String inStr) {
        this.textContent = inStr;
        return this;
    }

    private LineRecyclerViewAdapter setDataList() {
        getListPattern();
        this.dataList = ListString.set(this.textContent).getPatternList(this.listPattern);
        return this;
    }

    private LineRecyclerViewAdapter getPatterns() {
        getListPattern();
        getContentPattern();
        getTitlePattern();
        getSubPattern();
        return this;
    }

    ;

    private LineRecyclerViewAdapter getListPattern() {
        getPattern("listPattern");
        return this;
    }

    private LineRecyclerViewAdapter getContentPattern() {
        getPattern("contentPattern");
        return this;
    }

    private LineRecyclerViewAdapter getTitlePattern() {
        getPattern("titlePattern");
        return this;
    }

    private LineRecyclerViewAdapter getSubPattern() {
        getPattern("subPattern");
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

    LineRecyclerViewAdapter getDataList() {
        dataList = ListString.set(textContent).setDelimiter(listPattern).getPatternList();
        return this;
    }


    @Override
    public LineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.line_item_view, parent, false);
        return new LineItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineItemViewHolder holder, int position) {
        String s = dataList.get(position);
        String title = dataList.getItem(position).getPattern(titlePattern).toString();
        setTextView(holder.titleTextView, title);
        setTextView(holder.subContentTextView, dataList.getItem(position).getPattern(subPattern).toString());
        holder.contentTextView.setText(dataList.getItem(position).getRemain(titlePattern).getRemain(subPattern).getRemain(scopePattern).toString());
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
}
