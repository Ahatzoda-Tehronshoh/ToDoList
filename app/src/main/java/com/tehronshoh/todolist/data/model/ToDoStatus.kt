package com.tehronshoh.todolist.data.model

/**
 * Enum class representing the various statuses a To_Do item can have.
 */
enum class ToDoStatus {
    /** Task is waiting to be started by assignee */
    WAITING,

    /** Task is currently in process */
    IN_PROCESS,

    /** Task has been completed by assignee */
    DONE,

    /** Task has been closed by creator*/
    CLOSED
}