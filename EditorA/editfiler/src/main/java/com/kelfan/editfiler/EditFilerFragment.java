package com.kelfan.editfiler;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;
import com.kelfan.utillibrary.android.TouchHelper;

public class EditFilerFragment extends Fragment implements TouchHelper.ITouchHelper {

    private String filepath = "";
    private String fileContent = "";
    private EditText editText;
    private ImageButton imageButton;
    private int currentItem = -1;
    private LineRecyclerViewAdapter lineRecyclerViewAdapter;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public EditFilerFragment setFilepath(String path) {
        filepath = path;
        return this;
    }

    public boolean saveNewItem() {
        String text = editText.getText().toString();
        if (currentItem < 0) {
            if (!text.equals("")) {
                if (Boolean.valueOf(lineRecyclerViewAdapter.recordTime)) {
                    text = Xmler.set(text, LineRecyclerViewAdapter.recordTag).setContent(TimeWorker.getLocalTime()).toString();
                }
                lineRecyclerViewAdapter.addItem(text);
            }
        } else {
            if (Boolean.valueOf(lineRecyclerViewAdapter.updateTime)) {
                text = Xmler.set(text, LineRecyclerViewAdapter.updateTag).setContent(TimeWorker.getLocalTime()).toString();
            }
            lineRecyclerViewAdapter.setData(text, currentItem);
        }
        boolean result = save();
        lineRecyclerViewAdapter.notifyDataSetChanged();
        editText.setText("");
        currentItem = -1;
        return result;
    }

    public boolean save() {
        lineRecyclerViewAdapter.reverse();
        boolean result = FileWorker.writeToFile(filepath, lineRecyclerViewAdapter.getDataList().conbine(lineRecyclerViewAdapter.delimiter));
        lineRecyclerViewAdapter.reverse();
        return result;
    }

    public void sort() {
        lineRecyclerViewAdapter.sort();
        editText.setText("");
        currentItem = -1;
        lineRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editfiler_fragment, container, false);
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

        lineRecyclerViewAdapter = new LineRecyclerViewAdapter(this.getActivity(), fileContent);
        editfilerRecyclerView.setAdapter(lineRecyclerViewAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        editfilerRecyclerView.setLayoutManager(linearLayoutManager);
        lineRecyclerViewAdapter.setOnItemClickListener(new LineRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editText.setText(lineRecyclerViewAdapter.getText(position));
                currentItem = position;
            }
        });

        TouchHelper.newHelper(editfilerRecyclerView, lineRecyclerViewAdapter.getDataList(), this);
        return view;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public void clearEditText() {
        editText.setText("");
    }

    @Override
    public void swipeAction() {

    }

    @Override
    public void moveAction() {

    }

    @Override
    public void releaseAction() {
        currentItem = -1;
        editText.setText("");
        save();
    }
}
