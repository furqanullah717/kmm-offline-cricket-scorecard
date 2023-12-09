package com.codewithfk.eventhub.core.domain

import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource
import com.codewithfk.eventhub.scorecard.domain.model.Batsman
import com.codewithfk.eventhub.scorecard.domain.model.MatchState
import com.codewithfk.eventhub.scorecard.domain.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MatchEngine(private val dataSource: ScorecardDataSource) {

    private var currentMatchId: Long = -1

    suspend fun startNewMatch(
        team1Name: String,
        team2Name: String,
        totalOvers: Long,
        matchDate: String,
        venue: String,
        matchFormat: String
    ): Long {

        dataSource.insertMatchConfig(
            team1Name,
            team2Name,
            totalOvers,
            matchDate,
            venue,
            matchFormat
        )
        currentMatchId = dataSource.getLastEnteredID()
        initializeMatchState()
        return currentMatchId
    }

    private suspend fun initializeMatchState() {
        // Initialize the match state with default values
        dataSource.insertMatchState(
            currentMatchId,
            currentBatsmanOneId = -1,
            currentBatsmanTwoId = -1,
            currentBowlerId = -1
        )
    }

    suspend fun addBallDetail(
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
    ) {
        if (currentMatchId == -1L) throw IllegalStateException("No match is currently active.")

        dataSource.insertBallDetails(
            currentMatchId,
            overNumber,
            ballNumber,
            batsmanId,
            bowlerId,
            runsScored,
            extraType,
            extraRuns,
            extraDetails,
            wicketType,
            wicketDetails,
            playerDismissed
        )
        // Update match state and other statistics based on the ball detail
    }

    suspend fun updatePlayerStats(
        playerId: Long,
        runs: Long,
        ballsFaced: Long,
        fours: Long,
        sixes: Long,
        strikeRate: Double
    ) {
        dataSource.updateBatsmanStats(runs, ballsFaced, fours, sixes, strikeRate, playerId)
        // Further logic to reflect these stats in the current match
    }

    suspend fun changeBowler(newBowlerId: Long) {
        // Logic to change the bowler in the current match state
    }

    suspend fun changeBatsman(newBatsmanId: Long) {
        // Logic to change the batsman in the current match state
    }

    suspend fun endOver() {
        // Logic to handle the end of an over, including updating over count and switching strike
    }

    suspend fun updateMatchState(
        totalScore: Long,
        wicketsFallen: Long,
        oversBowled: Double,
        currentBatsmanOneId: Long,
        currentBatsmanTwoId: Long,
        currentBowlerId: Long
    ) {
        // Update the overall match state with new scores and statistics
        dataSource.updateMatchState(
            totalScore,
            wicketsFallen,
            oversBowled,
            currentBatsmanOneId,
            currentBatsmanTwoId,
            currentBowlerId,
            stateId = currentMatchId
        )
    }

    suspend fun endMatch() {
        // Logic to handle match completion, including final score tally and database updates
        currentMatchId = -1
    }

    fun getCurrentMatchState(): Flow<MatchState> {
        if (currentMatchId == -1L) throw IllegalStateException("No match is currently active.")
        return dataSource.getCurrentMatchState(currentMatchId)
    }

    fun getCurrentBatsmen(): Flow<Pair<Batsman, Batsman>> {
        return dataSource.getCurrentMatchState(currentMatchId).map {
            Pair(
                dataSource.getBatsman(it.currentBatsmanOneId!!).first(),
                dataSource.getBatsman(it.currentBatsmanTwoId!!).first()
            )
        }
    }

    fun getCurrentBowler(matchID: Long): Flow<Player> {
        return dataSource.getCurrentMatchState(matchID).map {
            dataSource.getPlayerById(it.currentBowlerId!!).first()
        }
    }

    // Additional methods can be added as needed for further match management
}
