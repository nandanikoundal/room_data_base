package com.nandani.room_data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase():RoomDatabase() {
    abstract fun userDao(): NotesDao

    companion object{
        var notesDatabase: NotesDatabase ?= null
        @Synchronized
        fun getDatabase(context: Context):NotesDatabase{
           if(notesDatabase == null){
               notesDatabase = Room.databaseBuilder(
                   context,
                   NotesDatabase::class.java,"notes"
               ).build()
           }
            return notesDatabase!!
        }

    }
}