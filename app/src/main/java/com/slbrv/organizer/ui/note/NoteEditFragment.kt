package com.slbrv.organizer.ui.note

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.entity.note.Note
import java.util.*

class NoteEditFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var noteTitleEditText: EditText
    private lateinit var noteContentEditText: EditText
    private lateinit var toolbar: Toolbar

    private lateinit var note: Note

    private var noteId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_note_edit, container, false)

        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        noteTitleEditText = root.findViewById(R.id.note_title_edit_text)
        noteContentEditText = root.findViewById(R.id.note_content_edit_text)
        toolbar = root.findViewById(R.id.toolbar_note_edit_fragment)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        toolbar.navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        noteId = arguments?.getLong("note_id") ?: 0
        Log.i("APP", "note id: $noteId")
        if (noteId > 0) {
            noteViewModel.get(noteId).observe(viewLifecycleOwner, {
                note = it
                noteTitleEditText.setText(note.title)
                noteContentEditText.setText(note.content)
            })
        } else {
            val time = Calendar.getInstance().time
            note = Note(null, "", "", time, time, "", false)
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        if (note.id == null) insertNote()
        else updateNote()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {

        }
        return true
    }

    private fun scrapeData(note: Note) {
        note.title = noteTitleEditText.text.toString()
        note.content = noteContentEditText.text.toString()
        note.editDate = Calendar.getInstance().time
        note.project = ""
        note.vanish = false
    }

    private fun insertNote() {
        scrapeData(note)
        if (note.title.isNotEmpty() || note.content.isNotEmpty()) {
            noteViewModel.insert(note)
        }
    }

    private fun updateNote() {
        scrapeData(note)
        if (note.title.isEmpty() && note.content.isEmpty()) {
            noteViewModel.delete(note)
        } else {
            noteViewModel.update(note)
        }
    }
}