package com.example.noteapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.NoteItemBinding

class NoteAdapter(private val noteList: ArrayList<NoteModel> = arrayListOf()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: NoteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding.txtNote.text = noteList[position].text
    }

    override fun getItemCount(): Int = noteList.count()

    fun setNotes(list: List<NoteModel>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)
}