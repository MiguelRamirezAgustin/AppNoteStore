package com.notasapp.notas.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteSectionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteSection(contac:NoteSection):Long

    @Query("SELECT * FROM noteSection ")
    fun getNoteSection():List<NoteSection>
}