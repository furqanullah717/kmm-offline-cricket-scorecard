package com.codewithfk.eventhub.scorecard.presentation.match_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.codewithfk.eventhub.di.AppModule
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MatchScreen(appModule: AppModule, navigator: Navigator) {
    Scaffold(modifier = Modifier.fillMaxSize().background(Color.Green)) { }
}