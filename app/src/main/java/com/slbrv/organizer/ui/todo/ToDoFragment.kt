package com.slbrv.organizer.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.R

class ToDoFragment : Fragment() {

    private lateinit var toDoViewModel: ToDoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        toDoViewModel =
                ViewModelProvider(this).get(ToDoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        toDoViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}