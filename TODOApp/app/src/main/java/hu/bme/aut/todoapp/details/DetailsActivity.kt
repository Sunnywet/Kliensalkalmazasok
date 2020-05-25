package hu.bme.aut.todoapp.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.todoapp.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TODO_NAME = "extra.todo_name"
        const val EXTRA_TODO_DESCR = "extra.todo_descr"
        const val EXTRA_TODO_DUE = "extra.todo_due"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        TodoName.text = intent.getStringExtra(EXTRA_TODO_NAME)
        Description.text = intent.getStringExtra(EXTRA_TODO_DESCR)
        DueTime.text = intent.getStringExtra(EXTRA_TODO_DUE)

        supportActionBar!!.title = getString(R.string.details, EXTRA_TODO_NAME)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initEdit()
    }

    private fun initEdit() {
        editButton.setOnClickListener {
            errorMessage()
        }
    }

    private fun errorMessage() {
        val builder = AlertDialog.Builder(this@DetailsActivity)
        builder.setTitle("You shall not pass!")
        builder.setMessage("To be implemented...")
        builder.setPositiveButton("OK") {dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}