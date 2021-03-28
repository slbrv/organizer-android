package com.slbrv.organizer.ui.note

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.note.NoteEntity
import java.util.*

class NoteEditFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var noteTitleEditText: EditText
    private lateinit var noteContentEditText: EditText
    private lateinit var noteProjectEditText: EditText
    private lateinit var toolbar: Toolbar

    private lateinit var noteEntity: NoteEntity

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
        noteProjectEditText = root.findViewById(R.id.note_project_edit_text)
        toolbar = root.findViewById(R.id.toolbar_note_edit_fragment)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        toolbar.navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        noteId = arguments?.getLong("note_id") ?: 0
        if (noteId > 0) {
            noteViewModel.get(noteId).observe(viewLifecycleOwner, {
                noteEntity = it
                noteTitleEditText.setText(noteEntity.title)
                noteContentEditText.setText(noteEntity.content)
                noteProjectEditText.setText(noteEntity.project)
            })
        } else {
            val time = Calendar.getInstance().time
            noteEntity = NoteEntity(null, "", "", time, time, "", false)
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        if (noteEntity.id == null) insertNote()
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

    private fun scrapeData(noteEntity: NoteEntity) {
        noteEntity.title = noteTitleEditText.text.toString()
        noteEntity.content = noteContentEditText.text.toString()
        noteEntity.editDate = Calendar.getInstance().time
        noteEntity.project = noteProjectEditText.text.toString()
        noteEntity.vanish = false
    }

    private fun insertNote() {
        scrapeData(noteEntity)
        if (noteEntity.title.isNotEmpty() || noteEntity.content.isNotEmpty()) {
            noteViewModel.insert(noteEntity)
        }
    }

    private fun updateNote() {
        scrapeData(noteEntity)
        if (noteEntity.title.isEmpty() && noteEntity.content.isEmpty()) {
            noteViewModel.delete(noteEntity)
        } else {
            noteViewModel.update(noteEntity)
        }
    }
}