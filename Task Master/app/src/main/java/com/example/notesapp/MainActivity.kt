package com.example.notesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewmodel
import com.example.notesapp.viewmodel.NoteviewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteviewmodel : NoteViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stetupModel()
        }

    private fun stetupModel(){
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderfactory = NoteviewModelFactory(application, noteRepository)
        noteviewmodel = ViewModelProvider(this,viewModelProviderfactory ).get(NoteViewmodel::class.java)
    }
    }
