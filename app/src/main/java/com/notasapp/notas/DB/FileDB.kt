package com.notasapp.notas.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = arrayOf(FileEntity::class), version = 1)
abstract class FileDB :RoomDatabase(){
    abstract fun fileDao():FileDAO
}