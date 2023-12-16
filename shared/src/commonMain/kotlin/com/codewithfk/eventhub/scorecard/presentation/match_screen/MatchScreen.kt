package com.codewithfk.eventhub.scorecard.presentation.match_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            CricketScorecard()
        }
    }
}

@Composable
fun CricketScorecard() {
    var score by remember { mutableStateOf(0) }
    var wickets by remember { mutableStateOf(0) }
    var overs by remember { mutableStateOf(0) }
    var balls by remember { mutableStateOf(0) }
    val currentOver = remember { mutableStateListOf<String>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text("Cricket ScoreCard")

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column(modifier = Modifier.weight(2f)) {
                    Text(text = "Batting Team", fontSize = 18.sp)
                    Text(text = "$score/$wickets ($overs.${balls})", fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "CRR")
                    Text(text = "0.0")
                }
            }
        }

        Spacer(modifier = Modifier.size(8.dp))
        Text("Batters", fontSize = 18.sp)
        Row {
            BatsmanStats("Furqan", 35, 34, modifier = Modifier.fillMaxWidth().weight(1f))
            Spacer(modifier = Modifier.size(16.dp))
            BatsmanStats("Bobby", 56, 24, modifier = Modifier.fillMaxWidth().weight(1f))
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text("Bowlers", fontSize = 18.sp)
        Row {
            BowlerStats("Nadeem", 3.4, 0, 0, 23, modifier = Modifier.fillMaxWidth().weight(1f))
            Spacer(modifier = Modifier.size(16.dp))
            BowlerStats("Sami", 3.0, 0, 0, 30, modifier = Modifier.fillMaxWidth().weight(1f))
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text("Current Over", fontSize = 18.sp)
        CurrentOver(currentOver.toList())

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            // Buttons for adding runs
            listOf(0, 1, 2, 3, 4, 5, 6).forEach { run ->
                Button(
                    onClick = {
                        score += run
                        balls = (balls + 1) % 6
                        currentOver.add(run.toString())
                        if (balls == 0) {
                            currentOver.clear()
                            overs++
                        }
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text(
                        "$run",
                        fontSize = 12.sp,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        Row {
            listOf("Wide", "No Ball", "Leg Byes", "Byes").forEach { run ->
                Button(onClick = {
                    val ball = when (run) {
                        "Wide" -> {
                            "Wd"
                        }

                        "No Ball" -> {
                            "Nb"
                        }

                        "Leg Byes" -> {
                            "Lb"
                        }

                        "Byes" -> {
                            "B"
                        }

                        else -> {
                            run
                        }
                    }
                    currentOver.add(ball)
                    score += 1

                }) {
                    Text(run)
                }
            }
        }
        // Button for wickets
        Button(onClick = {
            wickets++
            balls = (balls + 1) % 6
            if (balls == 0) {
                currentOver.clear()
                overs++
            }
            currentOver.add("W")
        }) {
            Text("Wicket")
        }
    }
}

@Composable
fun BatsmanStats(batsmanName: String, score: Int, balls: Int, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column(modifier = Modifier.weight(2f)) {
                Text(text = batsmanName, fontSize = 18.sp)
                Text(text = "$score/ (${balls})", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CurrentOver(list: List<String>) {
    LazyRow (modifier = Modifier.fillMaxWidth()) {
        items(list){
            val color =
                if (it == "W") MaterialTheme.colorScheme.error else if (it.contains("4") || it.contains(
                        "6"
                    )
                ) Color.Green else MaterialTheme.colorScheme.secondary
            Spacer(modifier = Modifier.size(3.dp))
            Box(
                modifier = Modifier.size(32.dp).clip(CircleShape)
                    .background(color)
            ) {
                Text(text = it, modifier = Modifier.align(Alignment.Center),color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.size(3.dp))
        }
    }
}

@Composable
fun BowlerStats(
    bowlerName: String,
    overs: Double,
    maiden: Int,
    wickets: Int,
    score: Int,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column(modifier = Modifier.weight(2f)) {
                Text(text = bowlerName, fontSize = 18.sp)
                Text(text = "$overs.$maiden.$wickets.$score", fontWeight = FontWeight.Bold)
            }
        }
    }
}
