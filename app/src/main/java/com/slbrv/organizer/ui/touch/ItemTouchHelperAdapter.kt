package com.slbrv.organizer.ui.touch

interface ItemTouchHelperAdapter {
    fun OnItemMove(from: Int, to: Int): Boolean
    fun onItemDismiss(position: Int)
}