package com.example.torcrawler.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torcrawler.data.api.AnalysisRequest
import com.example.torcrawler.data.api.RetrofitInstance
import com.example.torcrawler.data.model.AnalaysisResponse
import kotlinx.coroutines.launch

class DetailScreenViewModel: ViewModel() {

    private val apiService = RetrofitInstance.api

    private val _analysisResults = MutableLiveData<AnalaysisResponse?>(null)
    val analysisResult: LiveData<AnalaysisResponse?> = _analysisResults

    fun searchOnion(query: String) {
        try {
            viewModelScope.launch {
                val response = apiService.analyzeOnion(AnalysisRequest("http://silkr4lawr2zanmaqfbcpdtmybjmk2m6lu3iv5iw7jyivyz2cgt2xgid.onion/"))
                _analysisResults.value = response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}