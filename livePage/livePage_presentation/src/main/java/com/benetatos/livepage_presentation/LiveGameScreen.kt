package com.benetatos.livepage_presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benetatos.core_ui.LocalSpacing
import com.benetatos.core_ui.blue
import com.benetatos.core_ui.white
import com.benetatos.livepage_presentation.components.LivePage


@Composable
fun LiveGameScreen(
    viewModel: LivePageViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        snapshotFlow { viewModel.state.value }
            .collect { currentState ->
                Log.d("UI Debug", "Current State: $currentState")
            }
    }


    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().background(blue).padding(top = LocalSpacing.current.spaceLarge , bottom = LocalSpacing.current.spaceMedium)) {
                Text(modifier = Modifier.padding(start = LocalSpacing.current.spaceMedium), text = "Live Games", color = white)
            }

        }) { paddingValues ->

        when (state) {
            is LiveGamesState.Data -> {

                Log.d("marios stp ui ","data")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                   LivePage(data = state as LiveGamesState.Data, onEvent = viewModel::onEvent)
                }
            }

            is LiveGamesState.Error -> {
                Log.d("marios stp ui ","error")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text(text = (state as LiveGamesState.Error).errorMessage)
                }
            }

            is LiveGamesState.Loading -> {
                Log.d("marios stp ui ","Loading")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text(text = "Loading...")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LiveGameScreen()

}