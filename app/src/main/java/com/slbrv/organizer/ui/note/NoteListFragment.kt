package com.slbrv.organizer.ui.note

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.slbrv.organizer.Config
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.database.OrganizerDatabase
import com.slbrv.organizer.data.room.entity.note.Note
import kotlin.collections.ArrayList
import android.os.Bundle as Bundle

class NoteListFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val navController = NavHostFragment.findNavController(this)

        val addActionButton: FloatingActionButton = root.findViewById(R.id.note_add_action_button)
        addActionButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("note_id", 0)
            navController.navigate(R.id.nav_note, bundle)
        }

        val adapter = NoteRecyclerViewAdapter(context, ArrayList())

        noteViewModel.getAll().observe(viewLifecycleOwner, {
            adapter.update(it)
        })

        noteRecyclerView = root.findViewById(R.id.note_recycler_view)
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = adapter

        return root
    }
}