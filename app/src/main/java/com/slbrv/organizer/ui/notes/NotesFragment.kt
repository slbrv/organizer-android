package com.slbrv.organizer.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.slbrv.organizer.R

class NotesFragment : Fragment() {

    private lateinit var mNotesViewModel: NotesViewModel
    private lateinit var mNotesRecyclerView: RecyclerView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        //mNotesRecyclerView = root.findViewById(R.id.notes_recycler_view)

        mNotesViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}