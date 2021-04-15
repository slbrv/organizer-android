package com.slbrv.organizer.ui.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.note.NoteEntity
import java.text.DateFormat

class NoteRecyclerViewAdapter(
    private val context: Context?,
    private var notes: List<NoteEntity>
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.note_recycler_view_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = notes[position].title
        val content = notes[position].content

        holder.noteTitleTextView.text =
            if (title.isNotEmpty()) title
            else context?.resources?.getString(R.string.title)
        holder.noteContentTextView.text =
            if (content.isNotEmpty()) content
            else context?.resources?.getString(R.string.content)
        holder.noteProjectTextView.text = notes[position].project
        holder.noteCreationDateTextView.text =
            DateFormat.getDateInstance().format(notes[position].creationDate)
        holder.layout.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("note_id", position)
            Navigation
                .findNavController(holder.itemView)
                .navigate(R.id.nav_notes_to_nav_note_action, bundle)
        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: List<NoteEntity>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var noteTitleTextView: TextView = view.findViewById(R.id.note_title_text_view)
        var noteContentTextView: TextView = view.findViewById(R.id.note_content_part_text_view)
        var noteCreationDateTextView: TextView =
            view.findViewById(R.id.note_creation_date_text_view)
        var noteProjectTextView: TextView = view.findViewById(R.id.note_project_text_view)
        var layout: LinearLayout = view.findViewById(R.id.note_recycler_view_layout)
    }
}