package com.slbrv.organizer.ui.note

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.Config
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.note.NoteEntity
import java.util.*

class NoteEditFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var noteTitleEditText: EditText
    private lateinit var noteContentEditText: EditText
    private lateinit var noteProjectEditText: EditText

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

        noteId = arguments?.getLong("note_id") ?: 0
        Log.i("APP", "note_id: $noteId")
        if (noteId > 0) {
            noteViewModel.get(noteId).observe(viewLifecycleOwner, {
                noteEntity = it
                noteTitleEditText.setText(noteEntity.title)
                noteContentEditText.setText(noteEntity.content)
                noteProjectEditText.setText(noteEntity.project)
            })
        } else {
            noteEntity = NoteEntity()
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
        var project = noteProjectEditText.text.toString()
        if(project.length > Config.UI.MAX_PROJECT_NAME_LENGTH)
            project = project.substring(0, Config.UI.MAX_PROJECT_NAME_LENGTH)
        noteEntity.project = project
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