package com.lendly.fintech.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
 * Top bar principal de las pantallas de nivel raíz (Home, History, Manage, Loan info):
 * avatar (izquierda) · logo Lendly (centro) · campana de notificaciones (derecha).
 *
 * Reemplaza las implementaciones duplicadas que vivían dentro de cada pantalla.
 */
@Composable
fun MainTopBar(
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
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
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            LendlyLogoMark()
        }
        IconButton(onClick = onNotificationsClick) {
            Icon(
                painter = painterResource(R.drawable.ic_notifications),
                contentDescription = stringResource(R.string.home_notifications_content_description),
                tint = Color.Unspecified,
            )
        }
    }
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
