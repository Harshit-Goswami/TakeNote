package com.example.todoapp.room

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(var title: String, var content: String){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}
