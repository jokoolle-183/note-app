package com.example.noteapp.data.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.data.model.NoteModel

@Database(entities = [NoteModel::class],version = 1, exportSchema = false)
abstract class NoteDb: RoomDatabase() {
    abstract fun noteDao(): NoteDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: NoteDb? = null

        fun getInstance(context: Context): NoteDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): NoteDb {
            return Room.databaseBuilder(context.applicationContext, NoteDb::class.java, "NoteDb")
                .build()
        }
    }
}