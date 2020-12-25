package com.example.noteapp.di

import android.content.Context
import com.example.noteapp.data.model.db.NoteDao
import com.example.noteapp.data.model.db.NoteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDb {
        return NoteDb.getInstance(context)
    }

    @Provides
    fun provideNoteDao(noteDb: NoteDb): NoteDao {
        return noteDb.noteDao()
    }
}