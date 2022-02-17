package com.notasapp.notas.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteSectionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteSection(note:NoteSection):Long

    @Query("SELECT * FROM noteSection ")
    fun getNoteSection():List<NoteSection>

    @Query("DELETE FROM noteSection WHERE id=:parameterId")
    fun deleteSectionItem(parameterId:Int)

    @Query("UPDATE noteSection SET name=:updateName, dateCreate=:updateDate WHERE id=:idUpdate")
    fun getNoteSectionNoteUpdate(updateName:String, updateDate:String, idUpdate:Int)
}