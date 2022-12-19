package com.example.submission_1_fundamental.ui.setting

import android.content.Context
import android.content.SharedPreferences
import com.example.submission_1_fundamental.common.Constant

class SettingHelper(context : Context) {

    private val PREF_NAME = Constant.PREF_NAME
    private var sharedPref : SharedPreferences
    private val store : SharedPreferences.Editor

    init{
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        store = sharedPref.edit()
    }

    fun putSwitch(key : String, value : Boolean){
        store.putBoolean(key, value).apply()
    }

    fun getSwitch(key : String) : Boolean = sharedPref.getBoolean(key, false)



}