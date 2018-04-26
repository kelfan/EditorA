package com.kelfan.textfiler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFilerFragment extends Fragment {

    private String filepath = "";

    public TextFilerFragment setFilepath(String fpath) {
        this.filepath = fpath;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.textfiler_fragment, container, false);
        TextView textView = view.findViewById(R.id.textfiler_textview);
        String text = TextParser.getFileText(filepath);
        textView.setText(text);
        return view;
    }
}
