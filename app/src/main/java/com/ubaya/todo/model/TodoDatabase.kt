package com.ubaya.todo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.todo.util.DB_NAME
import com.ubaya.todo.util.MIGRATION_1_2
import com.ubaya.todo.util.MIGRATION_2_3

@Database(entities = arrayOf(Todo::class), version =  3)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context:Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "newtododb").addMigrations(MIGRATION_1_2).build()

        private fun buildDb(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java, "newtododb")
            .addMigrations(MIGRATION_2_3)
            .build()

        fun buildDbase(context: Context):TodoDatabase {
            val db = Room.databaseBuilder(context,
                TodoDatabase::class.java, DB_NAME)
                .addMigrations(MIGRATION_2_3)
                .build()

            return db
        }

        operator fun invoke(context:Context) {
            if(instance!=null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}
