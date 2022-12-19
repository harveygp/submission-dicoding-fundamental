package com.example.submission_1_fundamental.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.submission_1_fundamental.data.remote.response.UserItem

class UserDiffCallBack(private val mOldUserList: List<UserItem>, private val mNewUserList: List<UserItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }
    override fun getNewListSize(): Int {
        return mNewUserList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mNewUserList[newItemPosition]
        return oldEmployee.login == newEmployee.login && oldEmployee.id == newEmployee.id
    }
}