package com.example.instabug.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.instabug.model.HtmlData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PreferenceRepository(context: Context) {

     val PREFS_TAG = "SharedPrefs"
     val DATA_TAG = "MyData"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
     private var list : List<HtmlData> = listOf()

    fun <T> setList(key: String, list: MutableList<T>?) {
        val gson = Gson()
        val json: String = gson.toJson(list)
        set(key, json)
    }
    operator fun set(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

     fun getList() : List<HtmlData> {
        val arrayItems: List<HtmlData>
        val serializedObject = sharedPreferences.getString(DATA_TAG, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<HtmlData?>?>() {}.type
            arrayItems = gson.fromJson<List<HtmlData>>(serializedObject, type)
            list =  arrayItems
            Log.d("h" , arrayItems.toString())
        }
         return  list

     }
}