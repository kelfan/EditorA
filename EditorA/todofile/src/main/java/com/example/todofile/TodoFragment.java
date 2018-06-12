package com.example.todofile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.Xmler;

public class TodoFragment extends Fragment {

    String filePath;
    String fileContent = "";

    public static TodoFragment set(String filePath) {
        return new TodoFragment().withFilePath(filePath);
    }

    public TodoFragment withFilePath(String filePath) {
        this.filePath = filePath;
        this.fileContent = FileWorker.readSmallTxtFile(filePath);
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        EditText editText = view.findViewById(R.id.fragment_edit_text);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_recycler_view);
        RecyclerView.Adapter adapter;
        String style = Xmler.set(fileContent, Constant.style).getContent();
        if (style.equals(Constant.todo)) {
            adapter = ItemPresetAdapter.set(this.getActivity(), fileContent);
        } else {
            adapter = ItemPresetAdapter.set(this.getActivity(), fileContent);
        }
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }


}
