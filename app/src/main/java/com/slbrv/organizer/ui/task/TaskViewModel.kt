package com.slbrv.organizer.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

    }

    val text: LiveData<String> = _text
}