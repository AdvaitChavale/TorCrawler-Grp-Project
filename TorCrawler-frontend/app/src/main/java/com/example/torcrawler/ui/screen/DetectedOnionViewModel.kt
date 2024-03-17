package com.example.torcrawler.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torcrawler.data.api.RetrofitInstance
import com.example.torcrawler.data.api.SearchRequest
import kotlinx.coroutines.launch

class DetectedOnionViewModel: ViewModel() {

    private val apiService = RetrofitInstance.api

    private val _searchResults = MutableLiveData<List<String>>(emptyList())
    val searchResults: LiveData<List<String>> = _searchResults

    fun searchOnion(query: String) {
        try {
            viewModelScope.launch {
                val response = apiService.searchOnion(SearchRequest(query))
                _searchResults.value = response
            }
        } catch (e: Exception) {
            e.printStackTrace()
            searchOnion(query)
        }
    }
}