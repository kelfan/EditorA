package com.kelfan.utillibrary.android;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

public class TouchHelper {

    interface ITouchHelper {
        void swipeAction();

        void moveAction();
    }

    public static final int emptyFlag = 0;
    public static final int moveFlag = 1;

    public static ItemTouchHelper newHelper(final RecyclerView recyclerView, final List dataList, final LoopActor swipeActor, final LoopActor moveActor) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                if (fromPos < toPos) {
                    for (int i = fromPos; i < toPos; i++) {
                        Collections.swap(dataList, i, i + 1);
                    }
                } else {
                    for (int i = fromPos; i > toPos; i--) {
                        Collections.swap(dataList, i, i - 1);
                    }
                }
                recyclerView.getAdapter().notifyItemMoved(fromPos, toPos);
                recyclerView.setTag(moveFlag);
//                recyclerView.getAdapter().notifyDataSetChanged();
                if (moveActor != null) {
                    moveActor.run();
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                dataList.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
                if (swipeActor != null) {
                    swipeActor.run();
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getTag() != null) {
                    if (recyclerView.getTag().equals(moveFlag)) {
                        recyclerView.getAdapter().notifyDataSetChanged();
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


