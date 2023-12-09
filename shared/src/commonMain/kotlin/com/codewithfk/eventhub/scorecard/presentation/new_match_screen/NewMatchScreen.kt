package com.codewithfk.eventhub.scorecard.presentation.new_match_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewithfk.eventhub.core.presentation.stringResource
import com.codewithfk.eventhub.di.AppModule
import com.codewithfk.goodnight.MR
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

@Composable
fun NewMatchScreen(appModule: AppModule, navigator: Navigator) {

    val viewModel = getViewModel(key = "new_match_screen", factory = viewModelFactory {
        NewMatchViewModel(appModule.scoreCardDatabase)
    })


    val uiState = viewModel.screenState.collectAsState()

    var firstTeam by remember { mutableStateOf("") }
    var secondTeam by remember { mutableStateOf("") }
    var numberOfOver by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (uiState.value) {
                is NewMatchScreenState.Success -> {
                    LaunchedEffect(true) {
                        navigator.navigate(
                            "/match_screen", NavOptions(popUpTo = PopUpTo("/home", false))
                        )
                    }
                }

                is NewMatchScreenState.Error -> {
                    Text(text = stringResource(MR.strings.error))
                }

                is NewMatchScreenState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(48.dp))
                        Text(text = stringResource(MR.strings.loading))
                    }
                }

                else -> {
                    TextField(
                        value = firstTeam,
                        onValueChange = { firstTeam = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(MR.strings.first_name)) },
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    TextField(
                        value = secondTeam,
                        onValueChange = { secondTeam = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(MR.strings.second_name)) },
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    TextField(
                        value = numberOfOver,
                        onValueChange = { numberOfOver = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(MR.strings.no_of_overs)) },
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        onClick = {
                            if (firstTeam.isEmpty() || secondTeam.isEmpty() || numberOfOver.isEmpty()) {

                            } else {
                                viewModel.addNewMatchClicked(firstTeam, secondTeam, numberOfOver)
                            }
                        }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(MR.strings.start_match))
                    }
                }

            }
        }
    }
}