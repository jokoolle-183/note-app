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

    private val onClickDelete: (NoteModel) -> Unit  = { noteModel ->
        homeViewModel.deleteNote(noteModel)
    }

//    private val onClickEdit: (NoteModel) -> Unit = {noteModel ->
//        homeViewModel.onEditStart(noteModel)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteAdapter = NoteAdapter(onClickDelete = onClickDelete, viewModel = homeViewModel)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
        observeAllNotes()

        binding.btnAddNote.setOnClickListener {
            addNote()
        }
    }

    private fun observeAllNotes() {
        homeViewModel.allNotesLiveData.observe(viewLifecycleOwner) { noteList ->
            noteAdapter.setNotes(noteList)
        }
    }

    private fun addNote() {
        val noteText = binding.inputAddNote.text.toString()
        if(noteText.isNotEmpty()) {
            homeViewModel.addNote(NoteModel(text = noteText))
            binding.inputAddNote.text?.clear()
        }
    }
}