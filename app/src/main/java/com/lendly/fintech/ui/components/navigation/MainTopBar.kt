package com.lendly.fintech.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.brand.LendlyLogoMark
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

/**
 * Top bar único y componetizado de las pantallas principales (Home, Loan, Shop, History,
 * Manage y sus sub-pantallas).
 *
 * Es la MISMA barra en todas: estructura fija de tres zonas
 * — [leading] (izquierda: avatar / flecha back / nada)
 * — [title] (centro: logo o título)
 * — [actions] (derecha: íconos variables según la pantalla)
 * y cada pantalla pasa su propio contenido respetando sus íconos.
 *
 * Maneja su propio inset de status bar ([statusBarsPadding]) para quedar pegada arriba
 * (más arriba, sin el hueco que dejaba la versión anterior).
 */
@Composable
fun LendlyTopBar(
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val statusTopPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val topPadding = if (statusTopPadding > 8.dp) statusTopPadding - 8.dp else 0.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = topPadding)
            .padding(horizontal = Spacing.xs)
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leading != null) {
            leading()
        } else {
            Spacer(Modifier.width(Spacing.sm))
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            title?.invoke()
        }
        actions()
    }
}

/**
 * Preset de [LendlyTopBar] para las pantallas raíz: avatar (izquierda) · logo Lendly
 * (centro) · campana de notificaciones (derecha).
 */
@Composable
fun MainTopBar(
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LendlyTopBar(
        modifier = modifier,
        leading = {
            IconButton(onClick = onProfileClick) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(BackgroundCard),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_avatar_placeholder),
                        contentDescription = stringResource(R.string.home_profile_content_description),
                        tint = Color.Unspecified,
                    )
                }
            }
        },
        title = { LendlyLogoMark() },
        actions = {
            IconButton(onClick = onNotificationsClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_notifications),
                    contentDescription = stringResource(R.string.home_notifications_content_description),
                    tint = Color.Unspecified,
                )
            }
        },
    )
}

@Preview(name = "MainTopBar")
@Composable
private fun MainTopBarPreview() {
    LendlyTheme {
        Surface {
            MainTopBar()
        }
    }
}
