package com.example.noteapp.data.model.repo

import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.data.model.db.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class NoteRepo @Inject constructor(private val db: NoteDao) {

    fun getNotes(): Flow<List<NoteModel>> {
        return db.getNotes()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                print(e)
                emit(emptyList())
            }
    }

    suspend fun insertNote(note: NoteModel) {
        db.insertNote(note)
    }

    suspend fun deleteNote(note: NoteModel) {
        db.deleteNote(note)
    }

    suspend fun updateNote(note: NoteModel) {
        db.updateNote(note)
    }
}