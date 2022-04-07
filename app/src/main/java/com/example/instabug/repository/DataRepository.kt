package com.example.instabug.repository

import android.content.Context
import android.util.Log
import com.example.instabug.model.HtmlData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.lang.Exception


class DataRepository(context: Context) {

    var freqMap: Map<String, Int> = mapOf()
    var errorMessage: String = String()
    val dataList : MutableList<HtmlData> = mutableListOf()
     var sharedPreferences = PreferenceRepository(context)


    fun fetchData(){
        Thread {
            Log.d("data from repooooo 1" , freqMap.toString() )

            val list: MutableList<String> = mutableListOf()
            try {

                val doc: Document = Jsoup.connect("https://instabug.com/").get()
                val links: Elements = doc.select("a[href]")
                for (link in links) {
                    if (link.text() != "") {
                        val words = link.text().split(" ")
                        for (word in words) {
                            list.add(word)
                        }
                    }


                    }
                freqMap = list.groupingBy { it }.eachCount()
                val result = freqMap.toList().sortedBy { (_, value) -> value}.toMap()
                for(index in result.entries){
                    val item  = HtmlData(index.key , index.value)
                    dataList.add(item)
                }
                sharedPreferences.setList(sharedPreferences.DATA_TAG , dataList)
                Log.d("data from repo" , dataList.toString())

            } catch (ex: Exception) {
                errorMessage = ex.message.toString()
                ex.printStackTrace()
            }
        }.start()
    }


}