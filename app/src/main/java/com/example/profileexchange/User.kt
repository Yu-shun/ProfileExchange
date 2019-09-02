package com.example.profileexchange

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "name") var nameData: String? = null,
    @ColumnInfo(name = "git") var gitData: String? = null,
    @ColumnInfo(name = "twi") var twiData: String? = null

    /*override fun toString(): String{
        return uid.toString()
    }*/
)