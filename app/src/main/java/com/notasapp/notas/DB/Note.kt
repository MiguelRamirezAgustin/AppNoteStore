package com.notasapp.notas.DB

import android.app.Application
import androidx.room.Room

class Note :Application() {

    companion object{
        lateinit var dbNoteSection : NoteSectionsDB
        lateinit var dbSectionNote : SectionsNoteDB
    }

    override fun onCreate() {
        super.onCreate()
        dbNoteSection =  Room.databaseBuilder(this, NoteSectionsDB::class.java, "noteSectionDB.db").build()
        dbSectionNote =  Room.databaseBuilder(this, SectionsNoteDB::class.java, "sectionNoteDB.db").build()
    }
}