package com.benetatos.livepage_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().background(blue).padding(top = LocalSpacing.current.spaceLarge , bottom = LocalSpacing.current.spaceMedium)) {
                Text(modifier = Modifier.padding(start = LocalSpacing.current.spaceMedium), text = "Live Games", color = white)
            }

        }) { paddingValues ->

        when (val state =  viewModel.state.collectAsStateWithLifecycle().value) {
            is LiveGamesState.Data -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                   LivePage(data = state , onEvent = viewModel::onEvent)
                }
            }

            is LiveGamesState.Error -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text(text = state.errorMessage)
                }
            }

            is LiveGamesState.Loading -> {

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