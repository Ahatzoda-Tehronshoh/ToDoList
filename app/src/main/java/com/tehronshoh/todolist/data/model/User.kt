package com.tehronshoh.todolist.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index("email", unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val email: String,
    val password: String,
)