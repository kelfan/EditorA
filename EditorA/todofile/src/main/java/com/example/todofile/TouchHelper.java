package com.example.todofile;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kelfan.utillibrary.StringHashList;

public class TouchHelper {
    public static final int emptyFlag = 0;
    public static final int moveFlag = 1;

    public static ItemTouchHelper newHelper(final RecyclerView recyclerView, final SubRecyclerAdapter adapter) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                StringHashList list = (StringHashList) Hub.linkedHashMap.get(Hub.mainData);
                if (fromPos < toPos) {
                    for (int i = fromPos; i < toPos; i++) {
                        list.swapByKey(adapter.data.getKey(i), adapter.data.getKey(++i));
                        adapter.data.swap(i, i++);
                    }
                } else {
                    for (int i = fromPos; i > toPos; i--) {
                        list.swapByKey(adapter.data.getKey(i), adapter.data.getKey(--i));
                        adapter.data.swap(i, i--);
                    }
                }
                recyclerView.getAdapter().notifyItemMoved(fromPos, toPos);
                recyclerView.setTag(moveFlag);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                StringHashList list = (StringHashList) Hub.linkedHashMap.get(Hub.mainData);

                list.removeByKey(adapter.data.getKey(position));
//                adapter.data.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
                recyclerView.setTag(moveFlag);


            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getTag() != null) {
                    if (recyclerView.getTag().equals(moveFlag)) {
                        ItemPresetAdapter r = (ItemPresetAdapter) Hub.linkedHashMap.get(Hub.mainRecyclerAdapter);
                        r.notifyDataSetChanged();
                        recyclerView.setTag(emptyFlag);
                    }
                }
                super.clearView(recyclerView, viewHolder);
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags);
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
        ItemTouchHelper result = new ItemTouchHelper(itemTouchHelperCallback);
        result.attachToRecyclerView(recyclerView);
        return result;
    }
}
