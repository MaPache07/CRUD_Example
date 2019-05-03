package com.mapache.crud.data

import android.provider.BaseColumns

object DatabaseContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME = "name"
        const val COLUMN_MAIL = "mail"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_GENDER = "gender"
    }
}