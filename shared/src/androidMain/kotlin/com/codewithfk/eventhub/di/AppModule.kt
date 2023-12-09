package com.codewithfk.eventhub.di

import android.content.Context
import com.codewithfk.eventhub.core.data.DatabaseDriverFactory
import com.codewithfk.eventhub.scorecard.data.SqlDelightScorecardDataSource
import com.codewithfk.eventhub.scorecard.domain.data_source.ScorecardDataSource
import com.codewithfk.scorecard.shared.db.AppDatabase


actual class AppModule(
    private val context: Context
) {
    actual val scoreCardDatabase: ScorecardDataSource by lazy {
        SqlDelightScorecardDataSource(
            db = AppDatabase(
                driver = DatabaseDriverFactory(context).create(),
            ),
        )
    }

}