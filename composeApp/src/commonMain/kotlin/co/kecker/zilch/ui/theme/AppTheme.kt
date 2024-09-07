package co.kecker.zilch.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import co.kecker.zilch.determineTheme
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.darkColorScheme
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    theme: Theme = determineTheme(),
    content: @Composable () -> Unit
) {
    AdaptiveTheme(
        material = {
            MaterialTheme(
                colorScheme = if (useDarkTheme) {
                    darkScheme
                } else {
                    lightScheme
                },
                content = it
            )
        },
        cupertino = {
            CupertinoTheme(
                colorScheme = if (useDarkTheme) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                },
                content = it
            )

        },
        target = theme,
        content = content
    )
}






