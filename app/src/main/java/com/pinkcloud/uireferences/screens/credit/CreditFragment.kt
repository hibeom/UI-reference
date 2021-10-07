package com.pinkcloud.uireferences.screens.credit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.slider.Slider
import com.pinkcloud.uireferences.R
import com.pinkcloud.uireferences.databinding.FragmentCreditBinding

class CreditFragment : Fragment() {

    private lateinit var binding: FragmentCreditBinding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreditBinding.inflate(inflater, container, false)

        binding.scoreSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                binding.creditScoreView.score = slider.value.toInt()
            }
        })
        
        return binding.root
    }
}