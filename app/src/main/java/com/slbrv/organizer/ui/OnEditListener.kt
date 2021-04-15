package com.slbrv.organizer.ui

import android.view.View

interface OnEditListener : View.OnClickListener{
    fun onClick(index: Int, v: View)
}