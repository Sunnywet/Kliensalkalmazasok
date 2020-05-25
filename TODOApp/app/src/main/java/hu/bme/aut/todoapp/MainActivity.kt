package hu.bme.aut.todoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.todoapp.data.TodoDatabase
import hu.bme.aut.todoapp.data.TodoItem
import hu.bme.aut.todoapp.details.DetailsActivity
import hu.bme.aut.todoapp.details.StatisticsActivity
import hu.bme.aut.todoapp.fragments.AddTodoDialogFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(),
    TodoAdapter.OnTodoSelectedListener,
    TodoAdapter.TodoItemClickListener,
    TodoAdapter.TodoRemoveListener,
    AddTodoDialogFragment.AddTodoDialogListener,
    CoroutineScope by MainScope() {

    private lateinit var database: TodoDatabase
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = TodoDatabase.getDatabase(applicationContext)

        initRecyclerView()

        initFab()
        initStats()
    }

    private fun initFab() {
        fab.setOnClickListener{
            AddTodoDialogFragment()
                .show(supportFragmentManager, AddTodoDialogFragment::class.java.simpleName)
        }
    }

    private fun initStats() {
        statisticsButton.setOnClickListener {
            val statIntent = Intent(this, StatisticsActivity::class.java)
            startActivity(statIntent)
        }
    }

    private fun initRecyclerView() {
        MainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(this, this, this)
        MainRecyclerView.adapter = adapter
        loadTodosInBackground()
    }



    override fun onTodoSelected(todo: TodoItem?) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this@MainActivity, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_TODO_NAME, todo?.todoName.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_TODO_DESCR, todo?.description.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_TODO_DUE, todo?.due.toString())
        startActivity(showDetailsIntent)
    }

    override fun onTodoAdded(todo: TodoItem?) {
        addTodoInBackground(todo!!)
    }

    private fun addTodoInBackground(todo: TodoItem) = launch {
        withContext(Dispatchers.IO) {
            database.todoItemDao().insert(todo)
        }
        adapter.addTodo(todo)
    }

    private fun loadTodosInBackground() = launch {
        val todos = withContext(Dispatchers.IO) {
            database.todoItemDao().getAll()
        }
        adapter.update(todos)
    }

    override fun onTodoChanged(todo: TodoItem) {
        updateTodoInBackground(todo)
    }

    private fun updateTodoInBackground(todo: TodoItem) = launch {
        withContext(Dispatchers.IO) {
            database.todoItemDao().update(todo)
        }
        adapter.updateTodo(todo)
    }

    override fun onTodoRemoved(position: Int) {
        removeTodoInBackground(position)
    }

    private fun removeTodoInBackground(position: Int) = launch {
        withContext(Dispatchers.IO) {
            database.todoItemDao().delete(adapter.getTodoByPosition(position))
        }
        adapter.removeTodo(position)
    }
}
