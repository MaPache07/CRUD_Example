package com.mapache.crud.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${DatabaseContract.UserEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} TEXT PRIMARY KEY," +
                "${DatabaseContract.UserEntry.COLUMN_NAME} TEXT," +
                "${DatabaseContract.UserEntry.COLUMN_MAIL} TEXT," +
                "${DatabaseContract.UserEntry.COLUMN_PASSWORD} TEXT," +
                "${DatabaseContract.UserEntry.COLUMN_GENDER} TEXT,"

private const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${DatabaseContract.UserEntry.TABLE_NAME}"

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "userbase.db"
        const val DATABASE_VERSION = 1
    }
}