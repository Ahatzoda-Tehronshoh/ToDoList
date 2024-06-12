package com.tehronshoh.todolist.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [ForeignKey(
        entity = ToDo::class,
        parentColumns = ["id"],
        childColumns = ["todo_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("todo_id")]
)
data class ToDoComments(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "todo_id")
    val todoId: Long,
    val comment: String,
    val from: String,
    val date: String
)