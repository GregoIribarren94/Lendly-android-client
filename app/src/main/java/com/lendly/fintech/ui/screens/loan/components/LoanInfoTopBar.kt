package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun LoanInfoTopBar(
    onAvatarClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onAvatarClick) {
            Icon(
                painter = painterResource(R.drawable.ic_avatar_placeholder),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            LendlyLogo()
        }
        IconButton(onClick = onNotificationsClick) {
            Icon(
                painter = painterResource(R.drawable.ic_notifications),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

@Composable
private fun LendlyLogo() {
    // Misma composición del SplashScreen (frame 242.75 x 83.33, capas 213.77 x 47.1)
    // escalada a ~0.33 para entrar en la top bar.
    Box(
        modifier = Modifier.size(width = 80.dp, height = 28.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        Image(
            painter = painterResource(R.drawable.logo_rectangle_5),
            contentDescription = null,
            modifier = Modifier
                .size(width = 70.dp, height = 15.dp)
                .offset(x = 10.dp, y = 12.dp),
        )
        Image(
            painter = painterResource(R.drawable.logo_rectangle_4),
            contentDescription = null,
            modifier = Modifier
                .size(width = 70.dp, height = 15.dp)
                .offset(x = 5.dp, y = 6.dp),
        )
        Image(
            painter = painterResource(R.drawable.logo_rectangle_3),
            contentDescription = "Lendly Logo",
            modifier = Modifier.size(width = 70.dp, height = 15.dp),
        )
    }
}
