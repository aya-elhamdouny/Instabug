package com.example.instabug.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instabug.repository.PreferenceRepository

class MyViewModelFactory constructor(private val preferenceRepository: PreferenceRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.preferenceRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}