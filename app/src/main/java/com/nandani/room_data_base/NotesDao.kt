package com.nandani.room_data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {
    @Insert
    fun insertNotes(vararg notes: Notes)

    @Query("SELECT * FROM notes")
    fun getAll() : List<Notes>

    @Delete
    fun delete(vararg notes: Notes)
}