package com.codewithfk.eventhub.scorecard.domain.model

data class MatchState(
    val stateId: Long,
    val matchId: Long,
    val totalScore: Long,
    val wicketsFallen: Long,
    val oversBowled: Double,
    val currentBatsmanOneId: Long?,
    val currentBatsmanTwoId: Long?,
    val currentBowlerId: Long?
)
