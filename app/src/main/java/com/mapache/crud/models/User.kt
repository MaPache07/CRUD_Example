package com.mapache.crud.models

import android.os.Parcel
import android.os.Parcelable

data class User(var id: Int,
    var name: String,
    var mail: String,
    var password: String,
    var gender: String
) : Parcelable {
    constructor(parcel : Parcel) : this(
        id = parcel.readInt(),
        name = parcel.readString(),
        mail = parcel.readString(),
        password = parcel.readString(),
        gender = parcel.readString())
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(mail)
        parcel.writeString(password)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}