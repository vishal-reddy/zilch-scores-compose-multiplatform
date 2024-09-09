package co.kecker.zilch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.kecker.zilch.data.Score
import co.kecker.zilch.di.appModule
import co.kecker.zilch.ui.theme.AppTheme
import co.kecker.zilch.viewmodel.HomeViewModel
import co.kecker.zilch.viewmodel.UiEvent
import co.kecker.zilch.viewmodel.UiState
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(appModule())
        }
    ) {
        AppTheme {
            val viewModel: HomeViewModel = koinViewModel()

            when (val state = viewModel.uiState.collectAsState().value) {
                is UiState.Home ->
                    HomeScreen(state, updateData = { uiEvent -> viewModel.updateHomeData(uiEvent) })
            }
        }
    }

}

@OptIn(ExperimentalAdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(state: UiState.Home, updateData: (UiEvent) -> Unit = {}) {
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                showAddDialog = false
            }
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                var name by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Add player name") }
                )

                Spacer(modifier = Modifier.padding(16.dp))

                AdaptiveButton(
                    onClick = {
                        updateData(UiEvent.UpdateScores(state.data + Score(playerName = name)))
                        showAddDialog = false
                    }, modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank()
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }

    AdaptiveScaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog = true
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add score")
            }
        },
        topBar = {
            AdaptiveTopAppBar(
                title = {
                    Text("Zilch Scores")
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                LazyColumn {
                    items(state.data) {
                        var updateScoreDialog by remember { mutableStateOf(false) }

                        if (updateScoreDialog) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    updateScoreDialog = false
                                }
                            ) {
                                Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                                    var newScore by remember { mutableStateOf("") }

                                    OutlinedTextField(
                                        value = newScore,
                                        onValueChange = { newScore = removeNonNumericCharacters(it) },
                                        modifier = Modifier.fillMaxWidth(),
                                        label = { Text("Add to score") },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        ),

                                        )

                                    Spacer(modifier = Modifier.padding(16.dp))

                                    AdaptiveButton(
                                        onClick = {
                                            updateData(
                                                UiEvent.UpdateScores(
                                                    state.data.filterNot { score -> score.playerName == it.playerName } +
                                                        it.copy(
                                                            score = it.score + newScore.toInt(),
                                                            turn = it.turn + 1,
                                                            zilches = if (newScore.toInt() == 0) it.zilches + 1 else it.zilches
                                                        )))
                                            updateScoreDialog = false
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        enabled = newScore.isNotBlank()
                                    ) {
                                        Text("Update Score")
                                    }
                                }
                            }
                        }

                        ElevatedCard {
                            ListItem(
                                modifier = Modifier.clickable {
                                    updateScoreDialog = true
                                },
                                headlineContent = {
                                    Text(it.playerName)
                                },
                                supportingContent = {
                                    Row(
                                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Score - ${it.score}")

                                        Text("Zilches - ${it.zilches}")

                                        Text("Turn - ${it.turn}")

                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

fun removeNonNumericCharacters(input: String): String {
    return input.replace(Regex("[^0-9]"), "")
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(UiState.Home(listOf()))
    }
}