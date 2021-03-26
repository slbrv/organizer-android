package com.slbrv.organizer.ui.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.entity.note.NoteEntity
import java.text.DateFormat

class NoteRecyclerViewAdapter(private val notes: List<NoteEntity>) :

    RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.note_recycler_view_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTitleTextView?.text = notes[position].title
        holder.noteContentTextView?.text = notes[position].content
        holder.noteProjectTextView?.text = notes[position].project
        holder.noteCreationDateTextView?.text =
            DateFormat.getDateInstance().format(notes[position].creationDate)
    }

    override fun getItemCount() = notes.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var noteTitleTextView: TextView? = null
        var noteContentTextView: TextView? = null
        var noteCreationDateTextView: TextView? = null
        var noteProjectTextView: TextView? = null

        init {
            noteTitleTextView = view.findViewById(R.id.note_title_text_view)
            noteContentTextView = view.findViewById(R.id.note_content_part_text_view)
            noteCreationDateTextView = view.findViewById(R.id.note_creation_date_text_view)
            noteProjectTextView = view.findViewById(R.id.note_project_text_view)
        }
    }
}