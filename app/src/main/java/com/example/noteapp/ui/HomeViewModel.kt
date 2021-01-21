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

    private val _insertedNote = MutableLiveData<NoteModel>()
    val insertedNote: LiveData<NoteModel> = _insertedNote

    private val _deletedNotePosition = MutableLiveData<Int>()
    val deletedNotePosition: LiveData<Int> = _deletedNotePosition

    private val _updatedNotePosition = MutableLiveData<Pair<NoteModel,Int>>()
    val updatedNotePosition : LiveData<Pair<NoteModel,Int>> = _updatedNotePosition

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
           val notes =  noteRepo.getNotes()
            _allNotesLiveData.value = notes
        }
    }

    fun addNote(newNote: NoteModel) {
        viewModelScope.launch {
            val rowId = noteRepo.insertNote(newNote)
            _insertedNote.value = newNote.copy(id = rowId)
        }
    }

    fun deleteNote(noteModel: NoteModel, position: Int) {
        viewModelScope.launch {
            noteRepo.deleteNote(noteModel)
            _deletedNotePosition.value = position
        }
    }

    fun onEditCompleted(note: NoteModel, position: Int) {
        viewModelScope.launch {
            noteRepo.updateNote(note)
            _updatedNotePosition.value = Pair(note,position)
        }
    }
}

