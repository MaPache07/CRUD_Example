package com.mapache.crud.fragments

import android.os.Bundle
import android.provider.BaseColumns
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapache.crud.R
import com.mapache.crud.adapters.UserAdapter
import com.mapache.crud.data.Database
import com.mapache.crud.data.DatabaseContract
import com.mapache.crud.models.User
import kotlinx.android.synthetic.main.list_fragment.view.*

class ListFragment : Fragment() {

    lateinit var userAdapter: UserAdapter
    lateinit var users : ArrayList<User>
    var dbHelper = activity?.let { Database(it) }
    lateinit var itemView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        itemView = inflater.inflate(R.layout.list_fragment, container, false)
        insertData()
        return itemView
    }

    fun initRecycler(){
        userAdapter = UserAdapter(users)
        itemView.list_fragment.adapter = userAdapter
    }

    fun insertData(){
        val db = dbHelper?.readableDatabase

        val projection = arrayOf(
                DatabaseContract.UserEntry.COLUMN_NAME,
                DatabaseContract.UserEntry.COLUMN_MAIL,
                DatabaseContract.UserEntry.COLUMN_PASSWORD,
                DatabaseContract.UserEntry.COLUMN_GENDER
        )

        val sortOrder = "${DatabaseContract.UserEntry.COLUMN_NAME} DESC"

        val cursor = db?.query(
                DatabaseContract.UserEntry.TABLE_NAME, // nombre de la tabla
                projection, // columnas que se devolverán
                null, // Columns where clausule
                null, // values Where clausule
                null, // Do not group rows
                null, // do not filter by row
                sortOrder // sort order
        )

        if (cursor != null) {
            if(cursor.moveToFirst()){
                with(cursor) {
                    do{
                        var user = User(
                                getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME)),
                                getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_MAIL)),
                                getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_PASSWORD)),
                                getString(getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_GENDER))
                        )
                        users.add(user)
                    } while (this.moveToNext())
                }
            }
            initRecycler()
        }
    }
}