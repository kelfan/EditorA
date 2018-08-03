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
import android.widget.ImageButton;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.FileLocal;
import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.RegexWorker;
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;

public class TodoFragment extends Fragment {

    Long DEFAULT_CURRENT_ITEM = 0L;

    String filePath;
    ItemPresetAdapter adapter;
    String fileContent = "";
    public EditText editText;
    Long currentItem = DEFAULT_CURRENT_ITEM;


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
        // add
        ImageButton imageButton = view.findViewById(R.id.fragment_image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                currentItem = DEFAULT_CURRENT_ITEM;
            }
        });

        editText = view.findViewById(R.id.fragment_edit_text);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_recycler_view);
        String style = Xmler.set(fileContent, Constant.style).getContent();
        if (style.equals(Constant.todo)) {
            adapter = ItemPresetAdapter.set(this.getActivity(), fileContent);
        } else {
            adapter = ItemPresetAdapter.set(this.getActivity(), fileContent);
        }
        Hub.linkedHashMap.put(Hub.mainRecyclerAdapter, adapter);
        adapter.setOnItemClickListener(new ItemPresetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = adapter.presetList[position];
                if (!title.contains("/")) {
                    title += "/ ";
                }
                if (title.matches("# .*")) {
                    title = title.substring(2);
                }
                String text = editText.getText().toString();
                if (text.matches(".*/[\\S\\s]*")) {
                    String s = RegexWorker.matchAll(text, ".*/").get(0);
                    text = text.replace(s, "");
                }
                text = String.format("%s%s", title, text);
                editText.setText(text);
                editText.setSelection(editText.getText().length());
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Hub.linkedHashMap.put(Hub.mainFragment, this);
        return view;
    }


    public boolean saveNewItem() {
        String text = editText.getText().toString();
        text = Replacer.replaceTime(text);
        if (adapter.logTime) {
            if (!text.equals("")) {
                String time = TimeWorker.getDatetime(Replacer.DATE_TIME_FORMAT);
                if (!text.contains("@create_")) {
                    text = AtSign.set(text, "create").updateValue(time).toString();
                }
                text = AtSign.set(text, "modify").updateValue(time).toString();
            }
        }
        if (currentItem.equals(DEFAULT_CURRENT_ITEM)) {
            if (!text.equals("")) {
                adapter.data.add(text);
            }
        } else {
            if (!text.equals("")) {
                adapter.data.put(currentItem, text);
            }
        }
        adapter.notifyDataSetChanged();
        editText.setText("");
        currentItem = DEFAULT_CURRENT_ITEM;
        return save();
    }

    public boolean save() {
        editText.setText("");
        currentItem = DEFAULT_CURRENT_ITEM;
        return FileWorker.writeToFile(filePath, adapter.delimiter + adapter.data.combine(adapter.delimiter));
    }

    public void sort() {
        if (this.adapter.sortCreate){
            this.adapter.data.sort("create");
        }else{
            this.adapter.data.sort("date");
        }
        this.adapter.notifyDataSetChanged();
        editText.setText("");
        currentItem = DEFAULT_CURRENT_ITEM;
        save();
    }

    public boolean logItem(String text) {
        if (adapter.logDelete) {
            return FileLocal.set(filePath).addPostfix("_log").appendToFile(adapter.delimiter + text);
        }
        return false;
    }

}
