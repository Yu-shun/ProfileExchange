package com.example.profileexchange

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user ORDER BY name ASC")
    fun nametopAll(): List<User>

    @Query("SELECT * FROM user ORDER BY name DESC")
    fun namedownAll(): List<User>

    @Query("SELECT * FROM user ORDER BY twi ASC")
    fun twitopAll(): List<User>

    @Query("SELECT * FROM user ORDER BY twi DESC")
    fun twidownAll(): List<User>

    @Query("SELECT * FROM user ORDER BY git ASC")
    fun gittopAll(): List<User>

    @Query("SELECT * FROM user ORDER BY git DESC")
    fun gitdownAll(): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :search OR twi LIKE :search OR git LIKE :search")
    fun searchAllId(search: String): List<User>

    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(users: User)

    @Query("DELETE FROM user WHERE git = :id")
    fun deleteUser(id: String)

    @Query("DELETE FROM user WHERE uid = :id")
    fun deleteSelect(id: Int)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}