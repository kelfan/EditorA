package com.kelfan.editfiler;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;

public class EditFilerFragment extends Fragment {

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
            lineRecyclerViewAdapter.setData(text, currentItem);
        }
        int result = FileWorker.writeToFile(filepath, lineRecyclerViewAdapter.getDataList().conbine(lineRecyclerViewAdapter.delimiter));
        lineRecyclerViewAdapter.notifyDataSetChanged();
        editText.setText("");
        currentItem = -1;
        return result;
    }

    public int save() {
        return FileWorker.writeToFile(filepath, lineRecyclerViewAdapter.getDataList().conbine(lineRecyclerViewAdapter.delimiter));
    }

    public void sort() {
        lineRecyclerViewAdapter.sort();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        editfilerRecyclerView.setLayoutManager(linearLayoutManager);
        lineRecyclerViewAdapter.setOnItemClickListener(new LineRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editText.setText(lineRecyclerViewAdapter.getText(position));
                currentItem = position;
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                lineRecyclerViewAdapter.removeItem(position).notifyItemRemoved(position);
                save();
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(editfilerRecyclerView);
        return view;
    }
}
