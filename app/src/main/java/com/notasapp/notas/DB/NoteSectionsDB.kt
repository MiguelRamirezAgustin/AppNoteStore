package com.notasapp.notas.DB

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(NoteSection::class), version = 1)
abstract class NoteSectionsDB: RoomDatabase() {
    abstract fun noteSectionDao():NoteSectionDAO
}