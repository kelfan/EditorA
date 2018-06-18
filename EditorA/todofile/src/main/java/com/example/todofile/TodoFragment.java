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
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;

public class TodoFragment extends Fragment {

    String filePath;
    String fileContent = "";
    EditText editText;
    ItemPresetAdapter adapter;
    private int currentItem = -1;

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
        editText = view.findViewById(R.id.fragment_edit_text);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_recycler_view);
        String style = Xmler.set(fileContent, Constant.style).getContent();
        if (style.equals(Constant.todo)) {
            adapter = ItemPresetAdapter.set(this.getActivity(), fileContent);
        } else {
            adapter = ItemPresetAdapter.set(this.getActivity(), fileContent);
        }
        adapter.setOnItemClickListener(new ItemPresetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editText.setText(adapter.presetList[position].concat("/ "));
                editText.setSelection(editText.getText().length());
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }


    public boolean saveNewItem() {
        String text = editText.getText().toString();
        if (currentItem < 0) {
            if (!text.equals("")) {
                adapter.data.add(text);
            }
        } else {

        }
        adapter.notifyDataSetChanged();
        editText.setText("");
        currentItem = -1;
        return save();
    }

    public boolean save() {
        boolean result = FileWorker.writeToFile(filePath, adapter.data.combine(adapter.delimiter));
        return result;
    }

}
