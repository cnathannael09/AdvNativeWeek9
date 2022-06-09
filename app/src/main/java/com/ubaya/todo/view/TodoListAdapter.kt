package com.ubaya.todo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.todo.R
import com.ubaya.todo.databinding.TodoItemLayoutBinding
import com.ubaya.todo.model.Todo
import com.ubaya.todo.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),TodoCheckedChangeListener, TodoEditClick {

    class TodoViewHolder(var view:TodoItemLayoutBinding): RecyclerView.ViewHolder(view.root)
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TodoItemLayoutBinding>(inflater, R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.todo = todoList[position]
        holder.view.listener = this
//        holder.view.checkTask.setText(todoList[position].title.toString())

//        holder.view.imgEdit.setOnClickListener {
//            val action =
//                TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)
//
//            Navigation.findNavController(it).navigate(action)
//        }

        holder.view.checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked == true) {
//                adapterOnClick(todoList[position])
                todoList[position].is_done = 1
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckChanged(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(isChecked) {
//            adapterOnClick(obj) //delete obj from db
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodoFragment(uuid)

        Navigation.findNavController(v).navigate(action)
    }

}