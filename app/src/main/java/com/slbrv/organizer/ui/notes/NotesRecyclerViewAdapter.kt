package com.slbrv.organizer.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slbrv.organizer.R
import com.slbrv.organizer.data.entity.Note

class NotesRecyclerViewAdapter(private val notes: List<Note>) :

    RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.notes_recycler_view_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTitleTextView = notes.get(position).
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