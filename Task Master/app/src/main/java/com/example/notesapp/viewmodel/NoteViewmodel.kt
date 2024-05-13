package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewmodel(app:Application, private val noterepository: NoteRepository):AndroidViewModel(app){

    fun addNote(note: Note) =
        viewModelScope.launch{
            noterepository.insertNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch{
            noterepository.updateNote(note)
        }


    fun deleteNote(note: Note) =
        viewModelScope.launch{
            noterepository.deleteNote(note)
        }

    fun getAllNotes() = noterepository.getAllNotes()

    fun searchNote(query:String) =
        noterepository.searchNote(query)

}