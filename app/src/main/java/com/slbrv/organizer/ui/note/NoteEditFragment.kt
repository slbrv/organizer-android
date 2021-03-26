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

class NoteEditFragment : Fragment() {

    companion object {
        fun newInstance() = NoteEditFragment()
    }

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var noteTitleEditText: EditText
    private lateinit var noteContentEditText: EditText
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_note_edit, container, false)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteTitleEditText = root.findViewById(R.id.note_title_edit_text)
        noteContentEditText = root.findViewById(R.id.note_content_edit_text)
        toolbar = root.findViewById(R.id.toolbar_note_edit_fragment)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        toolbar.navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        toolbar.setNavigationOnClickListener{ activity?.onBackPressed() }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item) {

        }
        return true
    }
}