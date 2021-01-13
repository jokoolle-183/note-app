package com.example.noteapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.data.model.repo.NoteRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(private val noteRepo: NoteRepo) : ViewModel() {
    private val _allNotesLiveData = MutableLiveData<List<NoteModel>>()
    val allNotesLiveData: LiveData<List<NoteModel>> = _allNotesLiveData

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepo.getNotes().collect { notes ->
                _allNotesLiveData.value = notes
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

    fun onEditCompleted(note: NoteModel) {
        viewModelScope.launch {
            noteRepo.updateNote(note)
        }
    }
}

