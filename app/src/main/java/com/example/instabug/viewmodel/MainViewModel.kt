package com.example.instabug.viewmodel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instabug.model.HtmlData
import com.example.instabug.repository.PreferenceRepository

class MainViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {

    private val _data = MutableLiveData<List<HtmlData>>().apply { value = emptyList() }
    val data: LiveData<List<HtmlData>> = _data


    init {
        getData()
    }
    private fun getData() {
        val handler = Handler()
        handler.postDelayed({
            _data.value = preferenceRepository.getList()
            Log.d("data from viewmodel" ,_data.value.toString())
        }, 7000)


    }

}