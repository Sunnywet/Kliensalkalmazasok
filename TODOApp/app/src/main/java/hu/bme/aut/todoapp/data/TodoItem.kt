package hu.bme.aut.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoitem")
data class TodoItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,

    @ColumnInfo(name = "todo_name") var todoName: String,
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "due") var due: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean = false
)