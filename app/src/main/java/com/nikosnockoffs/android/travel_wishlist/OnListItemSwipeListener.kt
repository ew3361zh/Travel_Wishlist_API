package com.nikosnockoffs.android.travel_wishlist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface OnDataChangedListener {
    fun onListItemMoved(from: Int, to: Int) // moving positions on the list
    fun onListItemDeleted(position: Int)
}


class OnListItemSwipeListener(private val onDataChangedListener: OnDataChangedListener):
    ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN, // allows us to move/reorder things up or down
    ItemTouchHelper.RIGHT // for deleting
) {
    override fun onMove( // for moving up and down
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, // where item started before dragging
        target: RecyclerView.ViewHolder // where item picked up ends up
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition // where is it in the list
        val toPosition = target.adapterPosition // where was it moved to
        onDataChangedListener.onListItemMoved(fromPosition, toPosition)
        return true // return value is, are we going to commit the move,
                    // might be something to come back to if we want people not to be able to move something
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { // for swiping left/right
        if (direction == ItemTouchHelper.RIGHT) { // if user swipes right, delete the item
            onDataChangedListener.onListItemDeleted(viewHolder.adapterPosition) // delete item from position in list
        }
    }
}