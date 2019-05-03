package com.mapache.crud.fragments

import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mapache.crud.R
import com.mapache.crud.data.Database
import com.mapache.crud.data.DatabaseContract
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*

class LoginFragment : Fragment() {

    var dbHelper = activity?.let { Database(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.action_send.setOnClickListener{
            if(et_username.text.toString() == "" || et_mail.text.toString() == "" || et_password.text.toString() == "" || (!radio_male.isChecked && !radio_female.isChecked)){
                var toast = Toast.makeText(activity, "Ingrese todos los datos", Toast.LENGTH_SHORT);
                toast.show();
            } else{
                var gender = ""
                if(radio_male.isChecked) gender = "Male"
                if(radio_female.isChecked) gender = "Female"
                val db = dbHelper?.writableDatabase
                val values = ContentValues().apply {
                    put(DatabaseContract.UserEntry.COLUMN_NAME, et_username.text.toString())
                    put(DatabaseContract.UserEntry.COLUMN_MAIL, et_mail.text.toString())
                    put(DatabaseContract.UserEntry.COLUMN_PASSWORD, et_password.text.toString())
                    put(DatabaseContract.UserEntry.COLUMN_GENDER, gender)
                }
                val newRowId = db?.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values)
            }
        }

        return view
    }
}