package com.codewithfk.eventhub.scorecard.presentation.home

import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource
import com.codewithfk.eventhub.scorecard.domain.model.MatchConfigModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

class HomeViewModel(val dataSource: ScorecardDataSource, val navigator: Navigator) : ViewModel() {

    fun addNewMatchClicked() {
        navigator.navigate("/new_match")
    }

    val matches: Flow<List<MatchConfigModel>> = getAllMatches()
    private fun getAllMatches(): Flow<List<MatchConfigModel>> {
        return dataSource.getAllMatches()
    }
}