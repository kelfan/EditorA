package com.kelfan.editora;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelfan.utillibrary.FileWorker;

public class DefaultFragment extends Fragment {
    private String filepath = "";

    public Fragment setFilepath(String fpath) {
        this.filepath = fpath;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_fragment, container, false);
        TextView textView = view.findViewById(R.id.default_main_text_view);
        if (filepath.equals("")) {
            textView.setText("");
        }else {
            String fileContent = FileWorker.readSmallTxtFile(filepath);
            textView.setText(fileContent);
        }


        return view;
    }
}
