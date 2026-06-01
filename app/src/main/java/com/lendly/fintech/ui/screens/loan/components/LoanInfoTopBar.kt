package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.brand.LendlyLogoMark
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
            LendlyLogoMark()
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
