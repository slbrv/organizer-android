package com.slbrv.organizer.ui.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.R

class NoteEditFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_note_edit, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        return root
    }
}