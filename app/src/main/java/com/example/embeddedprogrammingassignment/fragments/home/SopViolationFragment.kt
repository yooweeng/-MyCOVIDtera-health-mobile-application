package com.example.embeddedprogrammingassignment.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.embeddedprogrammingassignment.R

class SopViolationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_home_sop_violation, container, false)
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            SopViolationFragment().apply {

            }
    }
}