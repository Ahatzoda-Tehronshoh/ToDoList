package com.tehronshoh.todolist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "todo",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["creator_email"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["assignee_email"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    @ColumnInfo(defaultValue = "")
    val description: String,
    val status: String,
    @ColumnInfo(name = "due_date")
    val dueDate: String, // Деддайн
    @ColumnInfo(name = "creator_email")
    val creatorEmail: String, // Email пользователя, создавшего задачу
    @ColumnInfo(name = "assignee_email")
    val assigneeEmail: String // Email пользователя, назначенного на выполнение задачи
) : Parcelable