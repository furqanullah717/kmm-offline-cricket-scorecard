package com.codewithfk.eventhub.scorecard.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewithfk.eventhub.core.presentation.stringResource
import com.codewithfk.eventhub.di.AppModule
import com.codewithfk.eventhub.utils.DateTime
import com.codewithfk.goodnight.MR
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HomeScreen(appModule: AppModule, navigator: Navigator) {

    val viewModel: HomeViewModel = getViewModel(key = "home_screen", factory = viewModelFactory {
        HomeViewModel(appModule.scoreCardDatabase, navigator)
    })

    val list = viewModel.matches.collectAsState(listOf())
    Scaffold(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    viewModel.addNewMatchClicked()
                }) {
                    Text(text = stringResource(MR.strings.new_match))
                }
            }

            items(list.value) { match ->
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(text = match.team1Name + " vs " + match.team2Name)
                    Text(text = match.totalOvers.toString() + " Overs")
                    match.matchDate?.toLongOrNull()?.let {
                        Text(text = it.toString())
                    }
                }
            }


        }
    }
}