package com.example.noteapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.NoteItemBinding
import com.example.noteapp.hideKeyboard
import com.example.noteapp.showKeyboard
import kotlinx.coroutines.ExperimentalCoroutinesApi


class NoteAdapter @ExperimentalCoroutinesApi constructor(
    private val viewModel: HomeViewModel,
    private val noteList: ArrayList<NoteModel> = arrayListOf(),
    private val onClickDelete: (NoteModel) -> Unit,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        with(holder) {
            binding.txtNote.text = note.text
            binding.btnDelete.setOnClickListener {
                onClickDelete(note)
            }
            binding.btnEdit.setOnClickListener {
                onEditStart()
            }

            binding.btnSave.setOnClickListener {
                onEditCompleted(note)
            }
        }
    }

    override fun getItemCount(): Int = noteList.count()

    fun setNotes(list: List<NoteModel>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onEditStart() {
            binding.apply {
                btnEdit.visibility = View.INVISIBLE
                btnSave.visibility = View.VISIBLE
                txtNote.visibility = View.INVISIBLE
                editTxtNote.visibility = View.VISIBLE
                editTxtNote.setOnFocusChangeListener { v, hasFocus ->
                    if(hasFocus) {
                        v.showKeyboard()
                    } else {
                        v.hideKeyboard()
                    }
                }
                editTxtNote.requestFocus()
                editTxtNote.setText(txtNote.text)
            }
        }

        @ExperimentalCoroutinesApi
        fun onEditCompleted(note: NoteModel) {
            binding.apply {
                val editedText = binding.editTxtNote.text.toString()
                viewModel.onEditCompleted(note.copy(text = editedText))
                btnEdit.visibility = View.VISIBLE
                btnSave.visibility = View.GONE
                txtNote.visibility = View.VISIBLE
                editTxtNote.visibility = View.GONE
            }
        }
    }
}