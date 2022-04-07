package com.example.instabug.viewmodel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instabug.model.HtmlData
import com.example.instabug.repository.Repository

class MainViewModel(val repository: Repository) : ViewModel() {

    private val _data = MutableLiveData<List<HtmlData>>().apply { value = emptyList() }
    val data: LiveData<List<HtmlData>> = _data

    init {
        repository.fetchData()
        getData()
    }

    private fun getData() {
        val handler = Handler()
        handler.postDelayed({
            _data.value = repository.dataList
            Log.d("data from viewmodel" ,_data.value.toString())
        }, 7000)


    }

}