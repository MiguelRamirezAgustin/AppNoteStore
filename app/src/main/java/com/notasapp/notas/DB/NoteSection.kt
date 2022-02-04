package com.notasapp.notas.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteSection")
data class NoteSection(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "color")
    var color:String,
    @ColumnInfo(name = "dateCreate")
    var dateCreate:String
){}

