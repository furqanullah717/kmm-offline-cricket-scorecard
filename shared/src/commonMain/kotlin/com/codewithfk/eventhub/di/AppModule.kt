package com.codewithfk.eventhub.di

import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource


expect class AppModule {
    val scoreCardDatabase: ScorecardDataSource

}