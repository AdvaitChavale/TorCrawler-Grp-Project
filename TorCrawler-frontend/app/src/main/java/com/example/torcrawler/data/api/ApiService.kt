package com.example.torcrawler.data.api

import com.example.torcrawler.data.model.AnalaysisResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/onion_search/")
    suspend fun searchOnion(@Body request: SearchRequest): List<String>

    @POST("/onion_analysis/")
    suspend fun analyzeOnion(@Body request: AnalysisRequest): AnalaysisResponse
}

data class SearchRequest(
    val search_term: String
)

data class AnalysisRequest(
    val url: String
)