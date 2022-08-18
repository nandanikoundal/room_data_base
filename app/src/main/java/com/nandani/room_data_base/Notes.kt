package com.nandani.room_data_base

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Notes {
    @PrimaryKey (autoGenerate = true)
    var id : Int = 0
    @ColumnInfo(name = "title") var title : String?=null
    @ColumnInfo(name = "description") var description:String?=null
    @ColumnInfo(name = "data") var data:String?=null
}