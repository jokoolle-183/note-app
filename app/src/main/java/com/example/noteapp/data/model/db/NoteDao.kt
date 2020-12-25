package com.example.noteapp.data.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.data.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM notemodel")
    fun getNotes(): List<NoteModel>

    @Insert
    fun insertNote(noteModel: NoteModel)

    @Insert
    fun prepoluate(notes: List<NoteModel>)
}