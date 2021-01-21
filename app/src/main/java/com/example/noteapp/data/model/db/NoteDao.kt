package com.example.noteapp.data.model.db

import androidx.room.*
import com.example.noteapp.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notemodel ORDER by id ")
    suspend fun getNotes(): List<NoteModel>

    @Insert
    suspend fun insertNote(noteModel: NoteModel): Long

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel)
}