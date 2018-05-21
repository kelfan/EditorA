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

import com.kelfan.utillibrary.FileLocal;
import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;

import java.util.Collections;

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
        int result = save();
        lineRecyclerViewAdapter.notifyDataSetChanged();
        editText.setText("");
        currentItem = -1;
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

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                if (fromPos < toPos) {
                    for (int i = fromPos; i < toPos; i++) {
                        Collections.swap(lineRecyclerViewAdapter.getDataList(), i, i + 1);
                    }
                } else {
                    for (int i = fromPos; i > toPos; i--) {
                        Collections.swap(lineRecyclerViewAdapter.getDataList(), i, i - 1);
                    }
                }
                lineRecyclerViewAdapter.notifyItemMoved(fromPos, toPos);
                editText.setText("");
                currentItem = -1;
                recyclerView.setTag("move");
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (Boolean.valueOf(lineRecyclerViewAdapter.archive)) {
                    FileLocal f = FileLocal.set(filepath).addPostfix("_archive");
                    f.appendToFile(lineRecyclerViewAdapter.getItem(position).concat(Xmler.set("", "complete_time").setContent(TimeWorker.getLocalTime()).toString()).concat(lineRecyclerViewAdapter.delimiter));
                }
                lineRecyclerViewAdapter.removeItem(position).notifyItemRemoved(position);
                lineRecyclerViewAdapter.notifyDataSetChanged();
                save();
                currentItem = -1;
                editText.setText("");
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if(recyclerView.getTag() != null ){
                    if (recyclerView.getTag().equals("move")){
                        lineRecyclerViewAdapter.notifyDataSetChanged();
                        recyclerView.setTag("");
                    }
                }
                super.clearView(recyclerView, viewHolder);
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(editfilerRecyclerView);
        return view;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public void clearEditText() {
        editText.setText("");
    }
}
