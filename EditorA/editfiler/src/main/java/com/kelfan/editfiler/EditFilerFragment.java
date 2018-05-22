package com.kelfan.editfiler;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kelfan.utillibrary.FileLocal;
import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;
import com.kelfan.utillibrary.android.TouchHelper;

import java.util.Collections;

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

    public int saveNewItem() {
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
            lineRecyclerViewAdapter.resetData().setData(text, currentItem);
            lineRecyclerViewAdapter.syncOriginal();
        }
        int result = save();
        editText.setText("");
        currentItem = -1;
        lineRecyclerViewAdapter.notifyDataSetChanged();
        return result;
    }

    public int save() {
        lineRecyclerViewAdapter.reverse();
        int result = FileWorker.writeToFile(filepath, lineRecyclerViewAdapter.getDataList().conbine(lineRecyclerViewAdapter.delimiter));
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
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lineRecyclerViewAdapter.filter(s.toString());
            }
        });

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
                String text = lineRecyclerViewAdapter.getText(position);
                editText.setText(text);
                currentItem = lineRecyclerViewAdapter.resetData().getDataList().getPosition(text);
                lineRecyclerViewAdapter.notifyDataSetChanged();
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
