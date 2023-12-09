package com.codewithfk.eventhub.scorecard.domain.model

data class Batsman(
    val batsmanId: Long,
    val name: String,
    val runs: Long,
    val ballsFaced: Long,
    val fours: Long,
    val sixes: Long,
    val strikeRate: Double
)
