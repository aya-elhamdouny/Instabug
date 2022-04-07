package com.example.instabug.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabug.R
import com.example.instabug.adapter.CustomAdapter
import com.example.instabug.model.HtmlData
import com.example.instabug.repository.DataRepository
import com.example.instabug.repository.PreferenceRepository
import com.example.instabug.viewmodel.MainViewModel
import com.example.instabug.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var customAdapter: CustomAdapter
    private lateinit var recyclerview : RecyclerView
    private lateinit var dataRepository: DataRepository
    private lateinit var viewModel: MainViewModel
    private lateinit var searchView: SearchView
    private var dataList : MutableList<HtmlData> = mutableListOf()
    private var filteredList : MutableList<HtmlData> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_bar)
        fetchData()
        initSearchView()
        initRecyclerView()
        initViewModel()

    }
    private  fun fetchData(){
        dataRepository = DataRepository(this)
        dataRepository.fetchData()
    }

    private  fun initSearchView(){
        searchView = findViewById(R.id.searchView)
        searchView.isSubmitButtonEnabled = true

        searchView.setOnCloseListener(object: SearchView.OnQueryTextListener,
            SearchView.OnCloseListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onClose(): Boolean {
                return false
            }

        })
        performSearch()
    }

    private fun initRecyclerView(){
        recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity) }
    }
    private fun performSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(PreferenceRepository(this))
        ).get(MainViewModel::class.java)

        viewModel.data.observe(this, Observer {
            if(it.isEmpty()){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.INVISIBLE
                Log.d("data from mainActivity" , it.toString())
                customAdapter = CustomAdapter(it)
                recyclerview.adapter = customAdapter
                customAdapter.notifyDataSetChanged()
                dataList = it.toMutableList()
            }
        })
    }

    private fun search(text: String?) {
        text?.let {
            Log.d("filterrr" , text)
            for (index in dataList) {
                if (index.word == text) {
                    val item = HtmlData(index.word, index.occurrence)
                    filteredList.add(item)
                }
            }
            Log.d("filterrr" , filteredList.toString())


            updateRecyclerView()
            if (dataList.isEmpty()) {
                Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView() {
        recyclerview.apply {
            Log.d("filterrr" , filteredList.toString())
            customAdapter = CustomAdapter(filteredList)
            customAdapter.notifyDataSetChanged()
        }
    }


}