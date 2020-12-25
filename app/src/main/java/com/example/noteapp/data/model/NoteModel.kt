package com.example.noteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val text: String
    )