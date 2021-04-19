package com.slbrv.organizer.ui.touch

import androidx.recyclerview.widget.RecyclerView

interface OnDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}