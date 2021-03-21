package com.slbrv.organizer.ui.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.R

class AlarmListFragment : Fragment() {

    private lateinit var alarmsViewModel: AlarmViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alarmsViewModel =
                ViewModelProvider(this).get(AlarmViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alarms, container, false)
        alarmsViewModel.text.observe(viewLifecycleOwner, {

        })
        
        return root
    }
}