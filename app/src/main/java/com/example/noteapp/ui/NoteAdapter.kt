package com.example.noteapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.NoteItemBinding

class NoteAdapter(private val noteList: ArrayList<NoteModel> = arrayListOf(),private val  onClickDelete: (NoteModel) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        with(holder) {
            binding.txtNote.text = note.text
            binding.btnDelete.setOnClickListener {
                onClickDelete(note)
            }
        }
    }

    override fun getItemCount(): Int = noteList.count()

    fun setNotes(list: List<NoteModel>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)
}