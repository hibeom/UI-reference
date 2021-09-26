package com.pinkcloud.uireferences

import androidx.navigation.NavDirections

data class Screen(
    val id: Int,
    val title: String,
    val content: String,
    val action: NavDirections
)
