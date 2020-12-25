package com.example.noteapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.DummyData
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var noteAdapter: NoteAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteAdapter = NoteAdapter()
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
        observeAllNotes()
    }

    fun observeAllNotes() {
        homeViewModel.allNotes.observe(viewLifecycleOwner) {noteList ->
            noteAdapter.setNotes(noteList)
        }
    }
}