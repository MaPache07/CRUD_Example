package com.mapache.crud

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.mapache.crud.data.Database
import com.mapache.crud.fragments.ListFragment
import com.mapache.crud.fragments.LoginFragment
import com.mapache.crud.utilities.AppConstants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    var flag = 0

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, LoginFragment()).commit()
                flag = 0
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, ListFragment()).commit()
                flag = 1
            }
            R.id.navigation_notifications -> {

                flag = 2
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            flag = savedInstanceState.get(AppConstants.FLAG_KEY) as Int
        }

        selectedItem()
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    fun selectedItem(){
        when (flag){
            0 -> {
                nav_view.selectedItemId = R.id.navigation_home
                supportFragmentManager.beginTransaction().replace(R.id.content_main, LoginFragment()).commit()
            }

            1 -> {
                nav_view.selectedItemId = R.id.navigation_dashboard
            }

            2 -> {
                nav_view.selectedItemId = R.id.navigation_notifications
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
