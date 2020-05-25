package hu.bme.aut.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.todoapp.data.DataManager
import hu.bme.aut.todoapp.data.TodoItem
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter (private val listener: OnTodoSelectedListener,
                   private val clickListener: TodoItemClickListener,
                   private val removeListener: TodoRemoveListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todos: MutableList<TodoItem> = ArrayList()
    private var doneTodos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todos[position]
        holder.bind(item)
    }

    fun getTodoByPosition(position: Int): TodoItem {
        return todos[position]
    }

    override fun getItemCount(): Int = todos.size

    private fun statCount() {
        doneTodos = 0
        for (todo in todos) {
            if (todo.isDone) {
                doneTodos++
            }
        }
        DataManager.setDoneNumber(doneTodos)
        DataManager.setTodoNumber(itemCount)
    }

    fun update(todosFromDB: List<TodoItem>) {
        todos.clear()
        todos.addAll(todosFromDB)
        statCount()
        notifyDataSetChanged()
    }

    fun updateTodo(todo: TodoItem?) {
        for (t in todos) {
            if (t.id == todo?.id) {
                todos[todos.indexOf(t)] = todo
            }
        }
        statCount()
    }

    fun addTodo(newTodo: TodoItem) {
        todos.add(newTodo)
        notifyItemInserted(todos.size - 1)
        statCount()
    }

    fun removeTodo(position: Int) {
        todos.removeAt(position)
        statCount()
        notifyItemRemoved(position)
        if (position < todos.size) {
            notifyItemRangeChanged(position, todos.size - position)
        }

    }

    inner class TodoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var item: TodoItem

        fun bind(newTodo: TodoItem) {
            item = newTodo
            itemView.TodoItemNameTextView.text = item.todoName
            itemView.cbIsDone.isChecked = item.isDone

            itemView.setOnClickListener{
                listener.onTodoSelected(item)
            }
            itemView.TodoItemRemoveButton.setOnClickListener {
                var index: Int = todos.indexOf(item)
                removeListener.onTodoRemoved(index)
            }
            itemView.cbIsDone.setOnCheckedChangeListener { buttonView, isChecked ->
                item.isDone = isChecked
                clickListener.onTodoChanged(item)
            }
        }

    }
    interface OnTodoSelectedListener {
        fun onTodoSelected(todo: TodoItem?)
    }

    interface TodoItemClickListener {
        fun onTodoChanged(todo: TodoItem)
    }

    interface TodoRemoveListener {
        fun onTodoRemoved (position: Int)
    }

}