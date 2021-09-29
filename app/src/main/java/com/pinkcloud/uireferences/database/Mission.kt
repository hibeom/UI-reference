package com.pinkcloud.uireferences.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mission_table")
data class Mission(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mission_id")
    val missionId: Int?,

    @ColumnInfo(name = "mission_name")
    var missionName: String,

    @ColumnInfo(name = "amount")
    var amount: Int
)
