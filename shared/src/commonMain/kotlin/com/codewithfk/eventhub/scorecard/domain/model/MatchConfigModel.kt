package com.codewithfk.eventhub.scorecard.domain.model

data class MatchConfigModel(
    val matchId: Long,
    val team1Name: String,
    val team2Name: String,
    val totalOvers: Int,
    val matchDate: String,
    val venue: String,
    val matchFormat: String
)