package com.example.noteapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.data.model.repo.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor (private val noteRepo: NoteRepo) : ViewModel() {
    private val _allNotes = MutableLiveData<List<NoteModel>>()
    val allNotes:LiveData<List<NoteModel>> = _allNotes

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { noteRepo.getNotes() }

            _allNotes.value = result
        }
    }
}

