package com.codewithfk.eventhub.di

import com.codewithfk.eventhub.core.data.DatabaseDriverFactory
import com.codewithfk.eventhub.scorecard.data.SqlDelightScorecardDataSource
import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource
import com.codewithfk.scorecard.shared.db.AppDatabase


actual class AppModule {
    actual val scoreCardDatabase: ScorecardDataSource by lazy {
        SqlDelightScorecardDataSource(
            db = AppDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }
}