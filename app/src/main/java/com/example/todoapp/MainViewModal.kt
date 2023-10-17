package com.example.todoapp

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.room.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModal @Inject constructor(private val repository: Repository) : ViewModel() {


    val notes: MutableState<List<NoteEntity>> = mutableStateOf(listOf())
    var note:MutableStateFlow<NoteEntity> = MutableStateFlow(NoteEntity("",""))
        fun setNote(note1: NoteEntity){
            note.value = note1
        }

    init {
        getNotes()
    }

    fun updateNote(note: NoteEntity) = viewModelScope.launch {
        repository.updateNote(note)
    }

    private fun getNotes() =
        viewModelScope.launch {
            repository.getNotes().catch { e ->
                Log.d("main", "Exception:${e.message}")
            }.collect {
                notes.value = it
            }
        }

    fun insertNote(note: NoteEntity) =
        viewModelScope.launch {
            repository.insertNote(note)

        }

    fun deleteNote(note: NoteEntity) =
        viewModelScope.launch {
            repository.deleteNote(note)
        }

}