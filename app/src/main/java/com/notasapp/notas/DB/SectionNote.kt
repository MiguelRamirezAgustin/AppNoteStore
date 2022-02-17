package com.notasapp.notas.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sectionNote")
data class SectionNote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "textContent")
    var textContent:String,
    @ColumnInfo(name = "dateCreate")
    var dateCreate:String,
    @ColumnInfo(name = "idSections")
    var idSections:Int,
    @ColumnInfo(name = "color")
    var color:String,
){}

