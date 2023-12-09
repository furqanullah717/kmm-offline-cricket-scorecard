package com.codewithfk.eventhub.scorecard.domain.data_source

import com.codewithfk.eventhub.scorecard.domain.model.Batsman
import com.codewithfk.eventhub.scorecard.domain.model.MatchState
import com.codewithfk.eventhub.scorecard.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface ScorecardDataSource {

    suspend fun insertMatchConfig(
        team1Name: String,
        team2Name: String,
        totalOvers: Long,
        matchDate: String,
        venue: String,
        matchFormat: String
    )

    suspend fun getLastEnteredID(): Long

    suspend fun insertBallDetails(
        matchId: Long,
        overNumber: Long,
        ballNumber: Long,
        batsmanId: Long,
        bowlerId: Long,
        runsScored: Long,
        extraType: String?,
        extraRuns: Long?,
        extraDetails: String?,
        wicketType: String?,
        wicketDetails: String?,
        playerDismissed: Long?
    )

    suspend fun updateBallDetails(
        overNumber: Long,
        ballNumber: Long,
        batsmanId: Long,
        bowlerId: Long,
        runsScored: Long,
        extraType: String?,
        extraRuns: Long?,
        extraDetails: String?,
        wicketType: String?,
        wicketDetails: String?,
        playerDismissed: Long?,
        ballId: Long
    )

    suspend fun insertBatsman(name: String)

    suspend fun updateBatsmanStats(
        runs: Long, ballsFaced: Long, fours: Long, sixes: Long, strikeRate: Double, batsmanId: Long
    )

    suspend fun insertMatchState(
        matchId: Long, currentBatsmanOneId: Long, currentBatsmanTwoId: Long, currentBowlerId: Long
    )

    suspend fun updateMatchState(
        totalScore: Long,
        wicketsFallen: Long,
        oversBowled: Double,
        currentBatsmanOneId: Long,
        currentBatsmanTwoId: Long,
        currentBowlerId: Long,
        stateId: Long
    )

    fun getBatsman(batsmanId: Long): Flow<Batsman>

    fun getCurrentMatchState(matchId: Long): Flow<MatchState>

    suspend fun insertPlayer(
        name: String,
        battingAverage: Double?,
        bowlingAverage: Double?,
        totalRuns: Long?,
        totalWickets: Long?,
        matchesPlayed: Long?,
        playerRole: String?
    )

    suspend fun updatePlayer(
        playerId: Long,
        name: String,
        battingAverage: Double?,
        bowlingAverage: Double?,
        totalRuns: Long?,
        totalWickets: Long?,
        matchesPlayed: Long?,
        playerRole: String?
    )

    fun getPlayerById(playerId: Long): Flow<Player>

    fun getAllPlayers(): Flow<List<Player>>
}