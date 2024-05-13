package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.Note
import kotlin.concurrent.Volatile

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile
        private var instence  : NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context:Context) = instence ?:
        synchronized(LOCK){
            instence ?:
            createDatabase(context).also{
                instence = it
            }
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()

        }


    }
