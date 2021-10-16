package com.pinkcloud.uireferences.screens.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pinkcloud.uireferences.R

class SuggestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            view.findViewById<TextView>(R.id.tv_title).also {
                it.text = getInt(ARG_POSITION).toString()
            }
        }
    }
}