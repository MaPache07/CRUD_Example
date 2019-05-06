package com.mapache.crud.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapache.crud.R
import com.mapache.crud.adapters.UserAdapter
import com.mapache.crud.models.User
import kotlinx.android.synthetic.main.list_fragment.view.*
import java.lang.Exception

class ListFragment : Fragment() {

    lateinit var users : ArrayList<User>
    var click : OnClickListener? = null

    interface OnClickListener{

        fun SetOnUpdateListener(user : User)

        fun SetOnDeleteListener(user : User)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnClickListener){
            click = context
        } else{
            throw Exception()
        }
    }

    companion object{
        fun newInstance(arrayUser : ArrayList<User>) : ListFragment{
            var newList = ListFragment()
            newList.users = arrayUser
            return newList
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        view.list_fragment_rv.adapter = UserAdapter(users, {user : User -> click?.SetOnUpdateListener(user)}, {user : User -> click?.SetOnDeleteListener(user)})
        val linearLayoutManager = LinearLayoutManager(this.context)
        view.list_fragment_rv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        click = null
    }
}