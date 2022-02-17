package com.notasapp.notas.DB

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(SectionNote::class), version = 1)
abstract class SectionsNoteDB: RoomDatabase() {
    abstract fun SectionNoteDao():SectionNoteDAO

}