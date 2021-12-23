package com.notasapp.notas.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fileEntity")
class FileEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEntity")
    var id:Int,
    @ColumnInfo(name = "nameEntity")
    var nameEntity:String,
    @ColumnInfo(name = "colorEntity")
    var colorEntity:String
        ){

}