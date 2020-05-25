package hu.bme.aut.todoapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.todoapp.R
import hu.bme.aut.todoapp.data.TodoItem
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.dialog_new_todo.view.*
import kotlinx.android.synthetic.main.dialog_new_todo.view.NewDue
import java.util.*

class AddTodoDialogFragment : AppCompatDialogFragment() {

    private lateinit var listener: AddTodoDialogListener
    private lateinit var contentView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = if (targetFragment != null){
                targetFragment as AddTodoDialogListener
            } else {
                activity as AddTodoDialogListener
            }
        } catch ( e: ClassCastException){
            throw RuntimeException(e)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        contentView = getContentView()
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_todo)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { _, _ ->
                if(isValid()) {
                    listener.onTodoAdded(getTodoItem())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun isValid(): Boolean {
        return contentView.NewTodoDialogEditText.text.isNotEmpty()
    }

    private fun getTodoItem(): TodoItem {
        return TodoItem(
            todoName = contentView.NewTodoDialogEditText.text.toString(),
            description = contentView.NewDescription.text.toString(),
            due = getDateFrom(contentView.NewDue),
            isDone = false
        )
    }


    private fun getDateFrom(picker: DatePicker): String {
        return String.format(
            Locale.getDefault(), "%04d.%02d.%02d.",
            picker.year, picker.month + 1, picker.dayOfMonth
        )
    }

    private fun getContentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.dialog_new_todo, null)
    }

    interface AddTodoDialogListener {
        fun onTodoAdded(todo: TodoItem?)
    }
}