package com.notasapp.notas.DB

import android.app.Application
import androidx.room.Room

class Note :Application() {

    companion object{
        lateinit var dbNoteSection : NoteSectionsDB
    }

    override fun onCreate() {
        super.onCreate()
        dbNoteSection =  Room.databaseBuilder(this, NoteSectionsDB::class.java, "sectionNote.db").build()
    }
}