package com.kelfan.editfiler;

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
import android.widget.TextView;

import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.Xmler;

public class EditFilerFragment extends Fragment {

    private String filepath = "";
    private String fileContent = "";
    private TextView textView;
    private EditText editText;
    private ImageButton imageButton;
    private int currentItem = -1;
    private LineRecyclerViewAdapter lineRecyclerViewAdapter;

    public EditFilerFragment setFilepath(String path) {
        filepath = path;
        return this;
    }

    public void saveNewItem() {
        String text = editText.getText().toString();
        if (currentItem < 0) {
            lineRecyclerViewAdapter.addItem(text);
        } else {
            lineRecyclerViewAdapter.setData(text, currentItem);
        }
        lineRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editfiler_fragment, container, false);
        textView = view.findViewById(R.id.editfiler_textview);
        textView.setText(filepath);
        editText = view.findViewById(R.id.editfiler_fragment_edit_text);
        imageButton = view.findViewById(R.id.editfiler_fragment_image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                currentItem = -1;
            }
        });
        this.fileContent = FileWorker.readSmallTxtFile(this.filepath);
        RecyclerView editfilerRecyclerView = view.findViewById(R.id.editfiler_recycler_view);
        String flag = Xmler.set(fileContent, "adapter").getContent();
        if (flag.equals("block")) {
            BlockRecyclerViewAdapter blockRecyclerViewAdapter = new BlockRecyclerViewAdapter(this.getActivity(), fileContent);
            editfilerRecyclerView.setAdapter(blockRecyclerViewAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
            editfilerRecyclerView.setLayoutManager(linearLayoutManager);
        } else {
            lineRecyclerViewAdapter = new LineRecyclerViewAdapter(this.getActivity(), fileContent);
            editfilerRecyclerView.setAdapter(lineRecyclerViewAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
            editfilerRecyclerView.setLayoutManager(linearLayoutManager);
            lineRecyclerViewAdapter.setOnItemClickListener(new LineRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    textView.setText(lineRecyclerViewAdapter.getText(position));
                    editText.setText(lineRecyclerViewAdapter.getText(position));
                    currentItem = position;
                }
            });
        }


        return view;
    }
}
