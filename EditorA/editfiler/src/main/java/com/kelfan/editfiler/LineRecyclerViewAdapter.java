package com.kelfan.editfiler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelfan.utillibrary.ListString;
import com.kelfan.utillibrary.Xmler;

public class LineRecyclerViewAdapter extends RecyclerView.Adapter<LineItemViewHolder> {
    private LayoutInflater inflater;
    private String textContent;
    private ListString dataList;
    private Context context;
    private String delimiter;

    public LineRecyclerViewAdapter(Context context, String text) {
        this.context = context;
        this.dataList = ListString.set(text);
        inflater = LayoutInflater.from(context);
        setContent(text).getDelimiter();
    }
    private LineRecyclerViewAdapter setContent(String inStr){
        this.textContent = inStr;
        this.dataList = ListString.set(inStr);
        return this;
    }

    private LineRecyclerViewAdapter getDelimiter(){
        delimiter = Xmler.set(textContent, delimiter).getContent();
        if (delimiter.equals("")){
            delimiter = "\n\r";
        }
        return this;
    }

    LineRecyclerViewAdapter getDataList(){
        dataList = ListString.set(textContent).setDelimiter(delimiter).getPatternList();
        return this;
    }



    @Override
    public LineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.line_item_view, parent, false);
        return new LineItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineItemViewHolder holder, int position) {
        String  s = dataList.get(position);
        holder.contentTextView.setText(s);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
