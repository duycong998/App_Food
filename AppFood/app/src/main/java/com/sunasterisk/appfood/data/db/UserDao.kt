package com.sunasterisk.appfood.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunasterisk.appfood.data.model.User

interface UserDao {
    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getDataUser() : List<User>

    @Delete
    fun deleteUsers(user: User)
}
