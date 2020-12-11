package com.sunasterisk.appfood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User (
    @SerializedName("idUser")
    @Expose
    @PrimaryKey
    var idUser: String = "",
    @SerializedName("userName")
    @Expose
    @PrimaryKey
    var userName: String = "",
    @SerializedName("passWord")
    @Expose
    @PrimaryKey
    var passWord: String = "",
    @SerializedName("fullName")
    @Expose
    @PrimaryKey
    var fullName: String = "",
    @SerializedName("mEmail")
    @Expose
    @PrimaryKey
    var mEmail: String = "",
    @SerializedName("mPhone")
    @Expose
    @PrimaryKey
    var mPhone: String = "",
)
