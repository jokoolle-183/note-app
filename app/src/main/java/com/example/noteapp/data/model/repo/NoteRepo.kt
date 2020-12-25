package com.example.noteapp.data.model.repo

import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.data.model.db.NoteDao
import com.example.noteapp.data.model.db.NoteDb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepo @Inject constructor(private val db: NoteDao) {

    suspend fun getNotes(): List<NoteModel> {
       return db.getNotes()
    }

    suspend fun insertNote(note: NoteModel) {
        db.insertNote(note)
    }
}