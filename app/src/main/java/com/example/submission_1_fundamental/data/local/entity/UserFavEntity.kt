package com.example.submission_1_fundamental.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userFav")
class UserFavEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int?,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String?,

    @field:ColumnInfo(name = "login")
    val login: String? = null,

)