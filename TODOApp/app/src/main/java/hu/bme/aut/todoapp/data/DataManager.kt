package hu.bme.aut.todoapp.data

object DataManager {
    private const val TODO_NUMBER = 0
    private const val DONE_TODO = 0

    var allTodo = TODO_NUMBER
    var doneTodo = DONE_TODO

    fun setTodoNumber(number: Int) {
        allTodo = number
    }

    fun getNotDoneNumber() : Int {
        return (allTodo - doneTodo)
    }

    fun setDoneNumber(number: Int) {
        doneTodo = number
    }

    fun getDoneNumber() : Int {
        return doneTodo
    }
}