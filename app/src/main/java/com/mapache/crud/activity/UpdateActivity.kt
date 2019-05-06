package com.mapache.crud.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mapache.crud.R
import com.mapache.crud.data.Database
import com.mapache.crud.data.DatabaseContract
import com.mapache.crud.fragments.LoginFragment
import com.mapache.crud.models.User
import com.mapache.crud.utilities.AppConstants
import kotlinx.android.synthetic.main.login_fragment.*

class UpdateActivity : AppCompatActivity(), LoginFragment.OnSendListener {

    lateinit var update : LoginFragment
    lateinit var user : User
    var dbHelper = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        user = intent?.extras?.getParcelable(AppConstants.USER_ID_KEY)!!
        update = LoginFragment.newInstance(user)
        supportFragmentManager.beginTransaction().replace(R.id.content_update, update).commit()
    }

    override fun setOnSendListener() {
        if(et_username.text.toString() == "" || et_mail.text.toString() == "" || et_password.text.toString() == "" || (!radio_male.isChecked && !radio_female.isChecked)) {
            var toast = Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT);
            toast.show()
        } else{
            val db = dbHelper.writableDatabase
            var gender = ""
            if(radio_male.isChecked) gender = "Male"
            if(radio_female.isChecked) gender = "Female"
            val id = user.id.toString()
            val name = et_username.text.toString().trim()
            val mail = et_mail.text.toString().trim()
            val pass = et_password.text.toString().trim()
            db.execSQL("UPDATE ${DatabaseContract.UserEntry.TABLE_NAME} SET ${DatabaseContract.UserEntry.COLUMN_NAME} = '${name}' WHERE " +
                    "${DatabaseContract.UserEntry.COLUMN_ID} = ${id}")
            db.execSQL("UPDATE ${DatabaseContract.UserEntry.TABLE_NAME} SET ${DatabaseContract.UserEntry.COLUMN_MAIL} = '${mail}' WHERE " +
                    "${DatabaseContract.UserEntry.COLUMN_ID} = ${id}")
            db.execSQL("UPDATE ${DatabaseContract.UserEntry.TABLE_NAME} SET ${DatabaseContract.UserEntry.COLUMN_PASSWORD} = '${pass}' WHERE " +
                    "${DatabaseContract.UserEntry.COLUMN_ID} = ${id}")
            db.execSQL("UPDATE ${DatabaseContract.UserEntry.TABLE_NAME} SET ${DatabaseContract.UserEntry.COLUMN_GENDER} = '${gender}' WHERE " +
                    "${DatabaseContract.UserEntry.COLUMN_ID} = ${id}")
            finish()
        }
    }
}
