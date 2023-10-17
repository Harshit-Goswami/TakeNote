package com.example.todoapp

import com.example.todoapp.room.NoteDao
import com.example.todoapp.room.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(val dao: NoteDao){

    suspend fun insertNote(note:NoteEntity) = withContext(Dispatchers.IO){
        dao.addNote(note)
    }

    fun getNotes():Flow<List<NoteEntity>> = dao.getAllNotes()

    suspend fun deleteNote(note:NoteEntity) = withContext(Dispatchers.IO){
        dao.deleteNote(note)
    }
    suspend fun updateNote(note:NoteEntity) = withContext(Dispatchers.IO){
        dao.updateNote(note)
    }

}