package com.slbrv.organizer.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.slbrv.organizer.R
import com.slbrv.organizer.data.entity.notes.NoteRoom
import java.util.*

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

        val content1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmo" +
                "d tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam" +
                " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo cons" +
                "equat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
                "dolore eu fugiat nulla pariatur."
        val content2 = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusan" +
                "tium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo invent" +
                "ore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo eni" +
                "m ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia " +
                "consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt"
        val notes = listOf(
            NoteRoom(0, "Note #1", content1, Date(), Date(), "Uni", true),
            NoteRoom(1, "Note #2", content2, Date(), Date(), "Super game", false),
            NoteRoom(2, "Note #3", content1, Date(), Date(), "Super uni", false)
        )

        noteRecyclerView = root.findViewById(R.id.note_recycler_view)
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = NoteRecyclerViewAdapter(notes)

        val navController = NavHostFragment.findNavController(this)

        val addActionButton: FloatingActionButton = root.findViewById(R.id.note_add_action_button)
        addActionButton.setOnClickListener{ navController.navigate(R.id.nav_note)}

        return root
    }
}