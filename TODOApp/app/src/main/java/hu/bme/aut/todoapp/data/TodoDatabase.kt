package hu.bme.aut.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    companion object {
        fun getDatabase(applicationContext: Context): TodoDatabase {
            return Room.databaseBuilder(applicationContext, TodoDatabase::class.java, "todo-list").build();
        }
    }

    abstract fun todoItemDao(): TodoItemDao
}