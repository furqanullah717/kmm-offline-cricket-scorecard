package com.codewithfk.eventhub.scorecard.data

import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource
import com.codewithfk.eventhub.scorecard.domain.model.Batsman
import com.codewithfk.eventhub.scorecard.domain.model.MatchState
import com.codewithfk.eventhub.scorecard.domain.model.Player
import com.codewithfk.scorecard.shared.db.AppDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class SqlDelightScorecardDataSource(db: AppDatabase) : ScorecardDataSource {
    private val queries = db.databaseQueries

    override suspend fun insertMatchConfig(
        team1Name: String,
        team2Name: String,
        totalOvers: Long,
        matchDate: String,
        venue: String,
        matchFormat: String
    ) {
        return queries.insertMatchConfig(
            team1Name, team2Name, totalOvers, matchDate, venue, matchFormat
        )
    }

    override suspend fun getLastEnteredID(): Long {
        return queries.getLastInsertedConnectionId().executeAsOne()
    }

    override suspend fun insertBallDetails(
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
    ) {
        return queries.insertBallDetails(
            matchId,
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
    }

    override suspend fun updateBallDetails(
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
    ) {
        queries.updateBallDetails(
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
            playerDismissed,
            ballId
        )
    }

    override suspend fun insertBatsman(name: String) {
        return queries.insertBatsman(name)
    }

    override suspend fun updateBatsmanStats(
        runs: Long, ballsFaced: Long, fours: Long, sixes: Long, strikeRate: Double, batsmanId: Long
    ) {
        queries.updateBatsmanStats(runs, ballsFaced, fours, sixes, strikeRate, batsmanId)
    }

    override suspend fun insertMatchState(
        matchId: Long, currentBatsmanOneId: Long, currentBatsmanTwoId: Long, currentBowlerId: Long
    ) {
        return queries.insertMatchState(
            matchId, currentBatsmanOneId, currentBatsmanTwoId, currentBowlerId
        )
    }

    override suspend fun updateMatchState(
        totalScore: Long,
        wicketsFallen: Long,
        oversBowled: Double,
        currentBatsmanOneId: Long,
        currentBatsmanTwoId: Long,
        currentBowlerId: Long,
        stateId: Long
    ) {
        queries.updateMatchState(
            totalScore,
            wicketsFallen,
            oversBowled,
            currentBatsmanOneId,
            currentBatsmanTwoId,
            currentBowlerId,
            stateId
        )
    }

    override fun getBatsman(batsmanId: Long): Flow<Batsman> {
        return queries.getBatsman(batsmanId).asFlow().mapToOne().map {
            Batsman(
                batsmanId = it.BatsmanID,
                name = it.Name,
                runs = it.Runs,
                ballsFaced = it.BallsFaced,
                fours = it.Fours,
                sixes = it.Sixes,
                strikeRate = it.StrikeRate
            )
        }
    }

    override fun getCurrentMatchState(matchId: Long): Flow<MatchState> {
        return queries.getCurrentMatchState(matchId).asFlow().mapToOne().map {
            MatchState(
                stateId = it.StateID,
                totalScore = it.TotalScore,
                wicketsFallen = it.WicketsFallen,
                oversBowled = it.OversBowled,
                currentBatsmanOneId = it.CurrentBatsmanOneID,
                currentBatsmanTwoId = it.CurrentBatsmanTwoID,
                currentBowlerId = it.CurrentBowlerID,
                matchId = it.MatchID
            )
        }
    }

    override suspend fun insertPlayer(
        name: String,
        battingAverage: Double?,
        bowlingAverage: Double?,
        totalRuns: Long?,
        totalWickets: Long?,
        matchesPlayed: Long?,
        playerRole: String?
    ) {
        return queries.insertPlayer(
            name, battingAverage, bowlingAverage, totalRuns, totalWickets, matchesPlayed, playerRole
        )
    }

    override suspend fun updatePlayer(
        playerId: Long,
        name: String,
        battingAverage: Double?,
        bowlingAverage: Double?,
        totalRuns: Long?,
        totalWickets: Long?,
        matchesPlayed: Long?,
        playerRole: String?
    ) {
        queries.updatePlayer(
            name,
            battingAverage,
            bowlingAverage,
            totalRuns,
            totalWickets,
            matchesPlayed,
            playerRole,
            playerId
        )
    }

    override fun getPlayerById(playerId: Long): Flow<Player> {
        return queries.getPlayerById(playerId).asFlow().mapToOne().map {
            Player(
                playerId = it.PlayerID,
                name = it.Name,
                battingAverage = it.BattingAverage,
                bowlingAverage = it.BowlingAverage,
                totalRuns = it.TotalRuns,
                totalWickets = it.TotalWickets,
                matchesPlayed = it.MatchesPlayed,
                playerRole = it.PlayerRole
            )
        }
    }

    override fun getAllPlayers(): Flow<List<Player>> {
        return queries.getAllPlayers().asFlow().mapToList().map { contactEntities ->
            supervisorScope {
                contactEntities.map {
                    async {
                        Player(
                            playerId = it.PlayerID,
                            name = it.Name,
                            battingAverage = it.BattingAverage,
                            bowlingAverage = it.BowlingAverage,
                            totalRuns = it.TotalRuns,
                            totalWickets = it.TotalWickets,
                            matchesPlayed = it.MatchesPlayed,
                            playerRole = it.PlayerRole
                        )
                    }
                }.map { it.await() }
            }
        }
    }
}