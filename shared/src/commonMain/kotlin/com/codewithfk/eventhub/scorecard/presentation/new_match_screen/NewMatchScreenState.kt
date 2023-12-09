package com.codewithfk.eventhub.scorecard.presentation.new_match_screen

sealed class NewMatchScreenState {
    object Idle : NewMatchScreenState()
    object Loading : NewMatchScreenState()
    object Success : NewMatchScreenState()
    data class Error(val message: String) : NewMatchScreenState()
}