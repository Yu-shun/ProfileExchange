package com.example.profileexchange

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        private  var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            val instance = instance
            if( instance != null){
                return instance
            }
            val instance_new = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "user").build()
            this.instance = instance_new
            return instance_new
        }
    }
}