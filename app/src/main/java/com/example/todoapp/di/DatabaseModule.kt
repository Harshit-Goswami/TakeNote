package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.room.NoteDao
import com.example.todoapp.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application):NoteDatabase=
        Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "Note_Database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db:NoteDatabase):NoteDao= db.NoteDao()
}