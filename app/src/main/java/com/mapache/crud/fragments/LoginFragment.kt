package com.mapache.crud.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapache.crud.R
import com.mapache.crud.models.User
import kotlinx.android.synthetic.main.login_fragment.view.*
import java.lang.Exception

class LoginFragment : Fragment() {

    var click : OnSendListener? = null
    var user : User? = null

    interface OnSendListener{
        fun setOnSendListener()
    }

    companion object{
        fun newInstance(user : User) : LoginFragment{
            var newFragment = LoginFragment()
            newFragment.user = user
            return newFragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnSendListener){
            click = context
        } else throw Exception()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        view.action_send.setOnClickListener{click?.setOnSendListener()}

        if(user != null){
            view.action_send.text = "update"
            view.et_username.text = Editable.Factory.getInstance().newEditable(user?.name)
            view.et_mail.text = Editable.Factory.getInstance().newEditable(user?.mail)
            view.et_password.text = Editable.Factory.getInstance().newEditable(user?.password)
            if(user?.gender == "Male") view.radio_male.isChecked = true
            else view.radio_female.isChecked = true
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        click = null
    }
}