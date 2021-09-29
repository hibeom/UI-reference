package com.pinkcloud.uireferences.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MissionDatabaseDao {

    @Insert
    suspend fun insert(mission: Mission)

    @Delete
    suspend fun delete(mission: Mission)

    @Update
    suspend fun update(mission: Mission)

    @Query("SELECT * FROM mission_table ORDER BY amount DESC")
    fun getAllMission(): LiveData<List<Mission>>
}