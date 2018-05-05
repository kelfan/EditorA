package com.kelfan.editfiler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelfan.utillibrary.FileWorker;

public class EditFilerFragment extends Fragment {

    private String filepath = "";
    private String fileContent = "";
    private BlockRecyclerViewAdapter blockRecyclerViewAdapter;

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

        RecyclerView blockRecyclerView = view.findViewById(R.id.block_recycler_view);
        this.fileContent = FileWorker.readSmallTxtFile(this.filepath);
        blockRecyclerViewAdapter = new BlockRecyclerViewAdapter(this.getActivity(),fileContent);
        blockRecyclerView.setAdapter(blockRecyclerViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        blockRecyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }
}
