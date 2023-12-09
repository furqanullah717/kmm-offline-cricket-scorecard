package com.codewithfk.eventhub.scorecard.domain.model

data class Player(
    val playerId: Long,
    val name: String,
    val battingAverage: Double?,
    val bowlingAverage: Double?,
    val totalRuns: Long?,
    val totalWickets: Long?,
    val matchesPlayed: Long?,
    val playerRole: String?
)

