package com.slbrv.organizer.ui.counters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.slbrv.organizer.R

class CountersFragment : Fragment() {

    private lateinit var countersViewModel: CountersViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        countersViewModel =
                ViewModelProvider(this).get(CountersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_counters, container, false)
        countersViewModel.text.observe(viewLifecycleOwner, {

        })
        return root
    }
}