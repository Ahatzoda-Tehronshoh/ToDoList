package com.tehronshoh.todolist.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "logged_in_user",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )])
data class LoggedInUser(
    @PrimaryKey val userId: Long
)