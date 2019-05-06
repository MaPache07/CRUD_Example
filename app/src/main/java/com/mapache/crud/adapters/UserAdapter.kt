package com.mapache.crud.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapache.crud.R
import com.mapache.crud.models.User
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserAdapter(var users : ArrayList<User>, val clickUpdate : (User) -> Unit, val clickDelete : (User) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(users[position], clickUpdate, clickDelete)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(user : User, clickUpdate : (User) -> Unit, clickDelete : (User) -> Unit) = with(itemView){
            username_text.text = user.name
            mail_text.text = user.mail
            gender_text.text = user.gender
            action_update.setOnClickListener{clickUpdate(user)}
            action_delete.setOnClickListener{clickDelete(user)}
        }
    }
}