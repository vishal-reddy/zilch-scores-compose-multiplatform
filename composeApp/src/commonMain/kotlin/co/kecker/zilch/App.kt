package co.kecker.zilch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.kecker.zilch.di.appModule
import co.kecker.zilch.ui.theme.AppTheme
import co.kecker.zilch.viewmodel.HomeViewModel
import co.kecker.zilch.viewmodel.UiState
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
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
                    HomeScreen(state, updateData = { viewModel.updateHomeData(it) })
            }
        }
    }

}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
private fun HomeScreen(state: UiState.Home, updateData: (String) -> Unit = {}) {
    AdaptiveScaffold {
        Text("Hello world.")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(UiState.Home("Hi"))
    }
}