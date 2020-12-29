package com.example.noteapp.data.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notemodel ORDER by id ")
    fun getNotes(): Flow<List<NoteModel>>

    @Insert
    suspend fun insertNote(noteModel: NoteModel)

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)

    @Insert
    suspend fun prepoluate(notes: List<NoteModel>)
}