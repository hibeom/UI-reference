package com.pinkcloud.uireferences.screens.donutview

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.pinkcloud.uireferences.database.Mission
import com.pinkcloud.uireferences.database.MissionDatabaseDao
import kotlinx.coroutines.launch

class DonutViewModel(
    private val databaseDao: MissionDatabaseDao, application: Application
) : AndroidViewModel(application) {

    private val _missionList = databaseDao.getAllMission()
    val missionList: LiveData<List<MissionItem>> = Transformations.map(_missionList) {
        it.map { mission ->
            var color: Int = Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random())
            itemColors.get(mission.missionId)?.let {
                color = it
            }
            itemColors.put(mission.missionId!!, color)
            MissionItem(mission.missionId, mission.missionName, mission.amount, color) }
    }

    private val itemColors = mutableMapOf<Int, Int>()

    fun insertMission() {
        viewModelScope.launch {
            val mission = Mission(null, missionName = "미션 ${('A'..'Z').random()}", amount = (1..100).random()*10)
            databaseDao.insert(mission)
        }
    }

    fun deleteMission(id: Int) {
        viewModelScope.launch {
            databaseDao.deleteById(id)
        }
    }

    fun updateMission(mission: Mission) {
        viewModelScope.launch {
            databaseDao.update(mission)
        }
    }
}