package com.notasapp.notas.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SectionNoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionNote(note:SectionNote):Long

    @Query("SELECT * FROM sectionNote WHERE idSections=:idSections")
    fun getNoteSectionNote(idSections:Int):List<SectionNote>

    /*@Query("DELETE FROM noteSection WHERE id=:parameterId")
    fun deleteSectionNoteItem(parameterId:Int)

    @Query("UPDATE noteSection SET name=:updateName, dateCreate=:updateDate WHERE id=:idUpdate")
    fun updateSectionsNote(updateName:String, updateDate:String, idUpdate:Int)*/
}