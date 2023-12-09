package com.codewithfk.eventhub.scorecard.domain.model

data class OverInfoModel(
    val overId: Long,
    val matchId: Long,
    val overNumber: Int,
    val bowlerId: Long,
    val runsConceded: Int,
    val wicketsTaken: Int,
    val extrasGiven: Int,
    val validBallsBowled: Int,
    val isCompleted: Boolean
)