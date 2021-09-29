package com.pinkcloud.uireferences.screens.donutview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pinkcloud.uireferences.database.MissionDatabaseDao

class DonutViewModelFactory(
    private val dataSource: MissionDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonutViewModel::class.java)) {
            return DonutViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}