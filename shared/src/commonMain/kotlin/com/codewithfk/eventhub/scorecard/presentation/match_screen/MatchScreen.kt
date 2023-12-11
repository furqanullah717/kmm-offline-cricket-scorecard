package com.codewithfk.eventhub.scorecard.presentation.match_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithfk.eventhub.di.AppModule
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MatchScreen(appModule: AppModule, navigator: Navigator) {
    Scaffold(modifier = Modifier.fillMaxSize().background(Color.Green)) {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(16.dp),
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(text = "Batting Team", fontSize = 18.sp)
                        Text(text = "100-1 (0.0/20)", fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "CRR")
                        Text(text = "0.0")
                    }
                }
            }
        }
    }
}