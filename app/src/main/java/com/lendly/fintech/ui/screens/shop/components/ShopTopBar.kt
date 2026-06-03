package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.components.brand.LendlyLogoMark
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun ShopTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(BackgroundCard)
                .clickable { },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = "Profile",
                tint = ContentOnSurface,
                modifier = Modifier.size(18.dp),
            )
        }
        Spacer(Modifier.weight(1f))
        LendlyLogoMark()
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.NotificationsNone,
            contentDescription = "Notifications",
            tint = ContentOnSurface,
            modifier = Modifier
                .size(24.dp)
                .clickable { },
        )
    }
}
