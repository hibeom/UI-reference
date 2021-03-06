package com.pinkcloud.uireferences.screens.donutview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.pinkcloud.uireferences.R
import com.pinkcloud.uireferences.database.AppDatabase
import com.pinkcloud.uireferences.database.Mission
import com.pinkcloud.uireferences.databinding.FragmentDonutBinding

class DonutFragment : Fragment() {

    private lateinit var binding: FragmentDonutBinding
    private lateinit var viewModel: DonutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireActivity().application
        val dataSource = AppDatabase.getInstance(requireContext()).missionDatabaseDao
        val donutViewModelFactory = DonutViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, donutViewModelFactory).get(DonutViewModel::class.java)
        binding.donutViewModel = viewModel

        val adapter = MissionAdapter { id -> viewModel.deleteMission(id) }
        binding.missionListView.adapter = adapter

        viewModel.missionList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.donutView.missionItems = it
        })

        val swipeHelperCallback = SwipeHelperCallback()
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.missionListView)

        binding.arrowBackView.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}