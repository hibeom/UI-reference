package com.pinkcloud.uireferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.pinkcloud.uireferences.databinding.FragmentScreenListBinding

/**
 * A fragment representing a list of Items.
 */
class ScreenListFragment : Fragment() {

    private var columnCount = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScreenListBinding.inflate(inflater, container, false)

        val screenList = arrayListOf(
            Screen(
                1,
                getString(R.string.dial_view),
                getString(R.string.dial_view),
                ScreenListFragmentDirections.actionScreenListFragmentToPieChartFragment()
            ),
            Screen(
                2,
                getString(R.string.donut_view),
                getString(R.string.donut_view),
                ScreenListFragmentDirections.actionScreenListFragmentToDonutFragment()
            ),
            Screen(
                3,
                getString(R.string.credit_score_view),
                getString(R.string.credit_score_view),
                ScreenListFragmentDirections.actionScreenListFragmentToCreditFragment()
            ),
            Screen(
                4,
                getString(R.string.custom_viewgroup),
                getString(R.string.custom_viewgroup),
                ScreenListFragmentDirections.actionScreenListFragmentToCustomLayoutFragment()
            ),
            Screen(
                5,
                getString(R.string.rating_view),
                getString(R.string.rating_view),
                ScreenListFragmentDirections.actionScreenListFragmentToRatingFragment()
            ),
        )

        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter =
                MyScreenRecyclerViewAdapter(screenList, this@ScreenListFragment.findNavController())
        }

        return binding.root
    }
}