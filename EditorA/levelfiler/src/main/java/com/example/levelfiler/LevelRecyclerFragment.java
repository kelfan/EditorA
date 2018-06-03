package com.example.levelfiler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LevelRecyclerFragment extends Fragment {

    String filePath;
    String fileContent = "hello, t1;test, t2, t3; test, t4;";

    public static LevelRecyclerFragment set(String filePath) {
        return new LevelRecyclerFragment().withFilePath(filePath);
    }

    public LevelRecyclerFragment withFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        EditText editText = view.findViewById(R.id.fragment_edit_text);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_recycler_view);
        LevelItemRecyclerAdapter levelItemRecyclerAdapter = LevelItemRecyclerAdapter.set(this.getActivity(), fileContent);
        recyclerView.setAdapter(levelItemRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}
