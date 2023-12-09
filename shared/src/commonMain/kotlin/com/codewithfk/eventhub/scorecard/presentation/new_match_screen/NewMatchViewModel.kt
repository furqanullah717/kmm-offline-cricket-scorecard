package com.codewithfk.eventhub.scorecard.presentation.new_match_screen

import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class NewMatchViewModel(private val dataSource: ScorecardDataSource) : ViewModel() {

    private val _screenState = MutableStateFlow<NewMatchScreenState>(NewMatchScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    fun addNewMatchClicked(firstTeam: String, secondTeam: String, numberOfOver: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _screenState.value = NewMatchScreenState.Loading
            delay(2000)
            try {
                dataSource.insertMatchConfig(
                    firstTeam,
                    secondTeam,
                    numberOfOver.toLong(),
                    Clock.System.now().toEpochMilliseconds().toString(),
                    null,
                    null
                )
                _screenState.value = NewMatchScreenState.Success
            } catch (e: Exception) {
                _screenState.value = NewMatchScreenState.Error(e.message ?: "Something went wrong")
            }
        }
    }

}