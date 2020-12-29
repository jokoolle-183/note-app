package com.example.noteapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.data.model.repo.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(private val noteRepo: NoteRepo) : ViewModel() {
    private val _allNotes = MutableLiveData<List<NoteModel>>()
    val allNotes: LiveData<List<NoteModel>> = _allNotes

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepo.getNotes().collect { notes ->
                _allNotes.value = notes
            }
        }
    }

    fun addNote(newNote: NoteModel) {
        viewModelScope.launch {
            noteRepo.insertNote(newNote)
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepo.deleteNote(noteModel)
        }
    }
}

