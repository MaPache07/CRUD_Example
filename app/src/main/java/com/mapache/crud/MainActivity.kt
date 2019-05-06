package com.mapache.crud

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mapache.crud.activity.UpdateActivity
import com.mapache.crud.data.Database
import com.mapache.crud.data.DatabaseContract
import com.mapache.crud.fragments.ListFragment
import com.mapache.crud.fragments.LoginFragment
import com.mapache.crud.models.User
import com.mapache.crud.utilities.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_fragment.*

class MainActivity : AppCompatActivity(), LoginFragment.OnSendListener, ListFragment.OnClickListener{

    var flag = 0
    var dbHelper = Database(this)
    var login = LoginFragment()
    var arrayUsers = ArrayList<User>()
    lateinit var list : ListFragment

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, login).commit()
                flag = 0
            }
            R.id.navigation_dashboard -> {
                list = ListFragment.newInstance(arrayUsers)
                supportFragmentManager.beginTransaction().replace(R.id.content_main, list).commit()
                flag = 1
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initArrayOrIndexOfUser(false)
        list = ListFragment.newInstance(arrayUsers)
        if (savedInstanceState != null) {
            flag = savedInstanceState.get(AppConstants.FLAG_KEY) as Int
        }

        selectedItem()
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    fun initArrayOrIndexOfUser(flag : Boolean) : Int {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DatabaseContract.UserEntry.COLUMN_ID,
            DatabaseContract.UserEntry.COLUMN_NAME,
            DatabaseContract.UserEntry.COLUMN_MAIL,
            DatabaseContract.UserEntry.COLUMN_PASSWORD,
            DatabaseContract.UserEntry.COLUMN_GENDER
        )

        val sortOrder = "${DatabaseContract.UserEntry.COLUMN_NAME} DESC"

        val cursor = db.query(
            DatabaseContract.UserEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            null, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            sortOrder // sort order
        )

        if (cursor != null) {
            var index = 1
            if (cursor.moveToFirst()) {
                with(cursor) {
                    do {
                        var user = User(
                            getInt(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_ID)),
                            getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME)),
                            getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_MAIL)),
                            getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_PASSWORD)),
                            getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_GENDER))
                        )
                        if(!flag){
                            arrayUsers.add(user)
                        } else{
                            index = user.id+1
                        }
                    } while (this.moveToNext())
                }
            }
            return index
        }
        return 0
    }

    override fun setOnSendListener() {
        if(et_username.text.toString() == "" || et_mail.text.toString() == "" || et_password.text.toString() == "" || (!radio_male.isChecked && !radio_female.isChecked)){
            var toast = Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT);
            toast.show()
        } else{
            var gender = ""
            if(radio_male.isChecked) gender = "Male"
            if(radio_female.isChecked) gender = "Female"
            val db = dbHelper?.writableDatabase
            val id = initArrayOrIndexOfUser(true)
            val values = ContentValues().apply {
                put(DatabaseContract.UserEntry.COLUMN_ID, id)
                put(DatabaseContract.UserEntry.COLUMN_NAME, et_username.text.toString())
                put(DatabaseContract.UserEntry.COLUMN_MAIL, et_mail.text.toString())
                put(DatabaseContract.UserEntry.COLUMN_PASSWORD, et_password.text.toString())
                put(DatabaseContract.UserEntry.COLUMN_GENDER, gender)
            }
            db?.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values)
            var user = User(id, et_username.text.toString(), et_mail.text.toString(), et_password.text.toString(), gender)
            arrayUsers.add(user)
        }
    }

    override fun SetOnUpdateListener(user : User) {
        var bundle = Bundle()
        bundle.putParcelable(AppConstants.USER_ID_KEY, user)
        startActivityForResult(Intent(this, UpdateActivity::class.java).putExtras(bundle), AppConstants.UPDATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        arrayUsers.clear()
        initArrayOrIndexOfUser(false)
        list = ListFragment.newInstance(arrayUsers)
        supportFragmentManager.beginTransaction().replace(R.id.content_main, list).commit()
    }

    override fun SetOnDeleteListener(user : User) {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM ${DatabaseContract.UserEntry.TABLE_NAME} WHERE ${DatabaseContract.UserEntry.COLUMN_ID} = ${user.id}")
        arrayUsers.remove(user)
        list = ListFragment.newInstance(arrayUsers)
        supportFragmentManager.beginTransaction().replace(R.id.content_main, list).commit()
    }

    fun selectedItem(){
        when (flag){
            0 -> {
                nav_view.selectedItemId = R.id.navigation_home
                supportFragmentManager.beginTransaction().replace(R.id.content_main, login).commit()
            }
            1 -> {
                nav_view.selectedItemId = R.id.navigation_dashboard
                supportFragmentManager.beginTransaction().replace(R.id.content_main, list).commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(AppConstants.FLAG_KEY, flag)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(flag != 0){
            nav_view.selectedItemId = R.id.navigation_home
        }
        else{
            moveTaskToBack(true)
        }
    }
}
