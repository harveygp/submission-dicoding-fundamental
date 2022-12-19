package com.example.submission_1_fundamental.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

	@field:SerializedName("items")
	val items: List<UserItem>? = null
)

data class UserItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("login")
	val login: String? = null,
)