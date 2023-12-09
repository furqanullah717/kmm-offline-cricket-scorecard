package com.codewithfk.eventhub.scorecard.domain.model

data class BallByBallModel(
    val ballId: Long,
    val matchId: Long,
    val overNumber: Int,
    val ballNumber: Int,
    val batsmanId: Long,
    val bowlerId: Long,
    val runsScored: Int,
    val extraType: String?,
    val extraRuns: Int?,
    val extraDetails: String?,
    val wicketType: String?,
    val wicketDetails: String?,
    val playerDismissed: Long?
)