package com.example.noteapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var noteAdapter: NoteAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private val onClickDelete: (NoteModel, Int) -> Unit  = { note, position ->
        homeViewModel.deleteNote(note,position)
    }

    private val onClickSave: (NoteModel, Int) -> Unit = { note, position ->
        homeViewModel.onEditCompleted(note,position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteAdapter = NoteAdapter(onClickDelete = onClickDelete, onClickSave = onClickSave)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
        observeNotes()

        binding.btnAddNote.setOnClickListener {
            addNote()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun observeNotes() {
        homeViewModel.allNotesLiveData.observe(viewLifecycleOwner) { noteList ->
            noteAdapter.setNotes(noteList)
        }

        homeViewModel.insertedNote.observe(viewLifecycleOwner) { note ->
            noteAdapter.insertNote(note)
        }

        homeViewModel.deletedNotePosition.observe(viewLifecycleOwner) {position ->
            noteAdapter.deleteNote(position)
        }

        homeViewModel.updatedNotePosition.observe(viewLifecycleOwner) {position ->
            noteAdapter.updateNote(position)
        }
    }

    private fun addNote() {
        val noteText = binding.inputAddNote.text.toString()
        if(noteText.isNotEmpty()) {
            val note = NoteModel(text = noteText)
            homeViewModel.addNote(note)
            binding.inputAddNote.text?.clear()
        }
    }
}