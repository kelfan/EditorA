package com.kelfan.editfiler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EditFilerFragment extends Fragment {

    private String filepath = "";

    public EditFilerFragment setFilepath(String path){
        filepath = path;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editfiler_fragment, container, false);
        TextView textView = view.findViewById(R.id.editfiler_textview);
        textView.setText(filepath);
        return view;
    }
}
