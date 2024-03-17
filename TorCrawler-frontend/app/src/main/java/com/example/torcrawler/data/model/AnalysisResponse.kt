package com.example.torcrawler.data.model

data class AnalaysisResponse(
    val header_tags: List<String>,
    val identified_threats: IdentifiedThreats,
    val images: List<Image>,
    val legal_risk: Int,
    val links: List<Link>,
    val market_place: String,
    val privacy_risk: Int,
    val security_risk_title: String,
    val threat_risk: Int,
    val title: String,
    val url: String
)