package com.example.todofile;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.kelfan.utillibrary.AtSign;
import com.kelfan.utillibrary.StringHashList;
import com.kelfan.utillibrary.TimeWorker;

import java.util.Date;

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
                String item = adapter.data.get(position);
                if (item.contains("@repeat_")) {
                    try {
                        Date date = TimeWorker.parseDate(AtSign.set(item, "date").getValue(), Replacer.TO_DATE);
                        String sRepeat = AtSign.set(item, "repeat").getValue();
                        if (sRepeat.contains("m")) {
                            int months = Integer.parseInt(sRepeat.replace("m", ""));
                            date = TimeWorker.addMonth(date, months);
                        } else if (sRepeat.contains("y")) {
                            int years = Integer.parseInt(sRepeat.replace("y", ""));
                            date = TimeWorker.addYear(date, years);
                        } else {
                            int days = Integer.parseInt(sRepeat);
                            date = TimeWorker.addDay(date, days);
                        }
                        String newItem = "@date_" + TimeWorker.formatDate(Replacer.DATE_FORMAT, date) + " " + AtSign.set(item, "date").getRemain();
                        list.add(newItem);
                    } catch (Exception ignored) {

                    }
                }
                list.removeByKey(adapter.data.getKey(position));
                if (item.contains("@repeaty_")) {
                    list.add(item);
                }
//                adapter.data.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
                recyclerView.setTag(moveFlag);
                TodoFragment todoFragment = (TodoFragment) Hub.linkedHashMap.get(Hub.mainFragment);
                if (direction == 32) {
                    item += " [complete(" + TimeWorker.getLocalTime() + ")]";
                } else {
                    item += " [delete(" + TimeWorker.getLocalTime() + ")]";
                }
                todoFragment.logItem(item);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getTag() != null) {
                    if (recyclerView.getTag().equals(moveFlag)) {
                        ItemPresetAdapter r = (ItemPresetAdapter) Hub.linkedHashMap.get(Hub.mainRecyclerAdapter);
                        r.notifyDataSetChanged();
                        TodoFragment todoFragment = (TodoFragment) Hub.linkedHashMap.get(Hub.mainFragment);
                        todoFragment.save();
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
