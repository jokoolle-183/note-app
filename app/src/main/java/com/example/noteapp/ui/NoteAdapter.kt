package com.example.noteapp.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.NoteItemBinding
import com.example.noteapp.hideKeyboard
import com.example.noteapp.showKeyboard
import kotlinx.coroutines.ExperimentalCoroutinesApi


class NoteAdapter constructor(
    private val noteList: ArrayList<NoteModel> = arrayListOf(),
    private val onClickDelete: (NoteModel, Int) -> Unit,
    private val onClickSave: (NoteModel, Int) -> Unit,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    //TODO: Implement DiffCallback for dispatching changes
    //TODO: Implement mechanism for handling item insertion, removal and updating
    //TODO: Remove Kotlin Flow, not suitable for this app

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        with(holder) {

            binding.apply {
                txtNote.text = note.text

                btnDelete.setOnClickListener {
                    onClickDelete(note, position)
                }
                btnEdit.setOnClickListener {
                    onEditStart()
                }

                btnSave.setOnClickListener {
                    onEditCompleted(note, position)
                }

                btnCancel.setOnClickListener {
                    onEditCanceled()
                }
            }

        }
    }

    override fun getItemCount(): Int = noteList.count()

    fun setNotes(list: List<NoteModel>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    fun insertNote(insertedNote: NoteModel) {
        notifyItemInserted(noteList.size)
        noteList.add(noteList.size, insertedNote)
    }

    fun deleteNote(position: Int) {
        notifyItemRemoved(position)
        noteList.removeAt(position)
    }

    fun updateNote(updatedNotePair: Pair<NoteModel, Int>) {
        val payload = updatedNotePair.first
        val position = updatedNotePair.second
        notifyItemChanged(position)
        noteList[position] = payload
    }

    inner class NoteViewHolder(val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var editedTextHolder = ""
        private val textChangeListener = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editedTextHolder = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                editedTextHolder = s.toString()
            }

        }
        fun onEditStart() {
            binding.apply {
                changeVisibility(btnEdit, txtNote, btnDelete, visibility = View.GONE)
                changeVisibility(btnSave, editTxtNote, btnCancel, visibility = View.VISIBLE)
                editTxtNote.apply {
                    setText(txtNote.text)
                    requestFocus()
                    setOnFocusChangeListener { v, hasFocus ->
                        if (hasFocus) {
                            v.showKeyboard()
                        } else {
                            v.hideKeyboard()
                        }
                    }
                 addTextChangedListener(textChangeListener)
                }
            }
        }

        fun onEditCanceled() {
            binding.apply {
                changeVisibility(btnEdit, txtNote, btnDelete, visibility = View.VISIBLE)
                changeVisibility(btnSave, editTxtNote, btnCancel, visibility = View.GONE)
                editTxtNote.setText(editedTextHolder)
                editTxtNote.removeTextChangedListener(textChangeListener)
            }
        }

        fun onEditCompleted(note: NoteModel, position: Int) {
            binding.apply {
                changeVisibility(btnEdit, txtNote, btnDelete, visibility = View.VISIBLE)
                changeVisibility(btnSave, editTxtNote, btnCancel, visibility = View.GONE)

                val editedNoteText = editTxtNote.text.toString()
                editTxtNote.removeTextChangedListener(textChangeListener)
                onClickSave(note.copy(text = editedNoteText), position)
            }
        }
    }

    private fun changeVisibility(vararg views: View, visibility: Int) {
        views.forEach {
            it.visibility = visibility
        }
    }
}