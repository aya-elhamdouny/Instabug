package com.example.instabug.repository

import android.content.Context
import android.util.Log
import com.example.instabug.model.HtmlData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.lang.Exception


class Repository(context: Context) {

    var freqMap: Map<String, Int> = mapOf()
    val dataList : MutableList<HtmlData> = mutableListOf()



    fun fetchData(){
        Thread {
            val list: MutableList<String> = mutableListOf()
            try {
                val doc: Document = Jsoup.connect("https://instabug.com/").get()
                val links: Elements = doc.select("a[href]")
                for (link in links) {
                    if (link.text() != "") {
                        list.add(link.text())
                    }

                }
                freqMap = list.groupingBy { it }.eachCount()
                val result = freqMap.toList().sortedBy { (_, value) -> value}.toMap()
                for(index in result.entries){
                    val item  = HtmlData(index.key , index.value)
                    dataList.add(item)
                }
                Log.d("data from repo" , dataList.toString())

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.start()
    }


}