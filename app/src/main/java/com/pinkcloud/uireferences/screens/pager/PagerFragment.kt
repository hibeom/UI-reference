package com.pinkcloud.uireferences.screens.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pinkcloud.uireferences.R

const val ARG_POSITION = "position"

class PagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pagerAdapter = PagerAdapter(this)
        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = pagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.title_suggest)
                1 -> getString(R.string.title_account)
                2 -> getString(R.string.title_contact)
                else -> getString(R.string.title_suggest)
            }
        }.attach()
    }
}

class PagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            0 -> SuggestFragment()
            1 -> AccountFragment()
            2 -> ContactFragment()
            else -> SuggestFragment()
        }
        fragment.arguments = Bundle().apply {
            putInt(ARG_POSITION, position)
        }
        return fragment
    }

}