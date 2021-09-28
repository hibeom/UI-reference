package com.pinkcloud.uireferences.screens.dialview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pinkcloud.uireferences.databinding.FragmentDialBinding

class DialFragment : Fragment() {

    private lateinit var binding: FragmentDialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialBinding.inflate(inflater, container, false)
        return binding.root
    }
}