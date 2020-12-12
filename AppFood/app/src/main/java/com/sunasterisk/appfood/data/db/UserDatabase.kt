package com.sunasterisk.appfood.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunasterisk.appfood.data.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class  UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabaseUser(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
