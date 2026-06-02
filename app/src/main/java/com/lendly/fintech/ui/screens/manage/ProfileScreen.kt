package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info as FilledInfo
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info as OutlinedInfo
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.ui.components.brand.LendlyLogoMark
import com.lendly.fintech.ui.theme.*

@Composable
fun ProfileScreen(
    onDetailClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: ManageViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileTopBar(onBack = onBack)

        ProfileHeader()

        HorizontalDivider(
            color = BorderNeutral,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        ProfileSectionLabel(title = "Account")

        ProfileMenuItem(
            icon = Icons.Outlined.PersonOutline,
            label = "Personal information",
            onClick = { onDetailClick("personal") },
        )
        ProfileMenuItem(
            icon = Icons.Filled.AccountBalance,
            label = "Payment methods",
            onClick = { onDetailClick("payment") },
        )
        ProfileMenuItem(
            icon = Icons.Filled.Settings,
            label = "Account settings",
            onClick = { onDetailClick("settings") },
        )
        ProfileMenuItem(
            icon = Icons.Filled.FilledInfo,
            label = "Address",
            onClick = { onDetailClick("address") },
        )

        Spacer(modifier = Modifier.height(Spacing.lg))
        HorizontalDivider(
            color = BorderNeutral,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )
        Spacer(modifier = Modifier.height(Spacing.lg))

        ProfileSectionLabel(title = "More")

        ProfileMenuItem(
            icon = Icons.Outlined.OutlinedInfo,
            label = "About Lendly",
            onClick = { onDetailClick("about") },
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Inbox,
            label = "Help center",
            onClick = { onDetailClick("help") },
        )
        ProfileMenuItem(
            icon = Icons.Filled.Check,
            label = "Privacy & security",
            onClick = { onDetailClick("security") },
        )
        ProfileMenuItem(
            icon = Icons.Outlined.NotificationsNone,
            label = "Notifications",
            onClick = { onDetailClick("notifications") },
        )

        Spacer(modifier = Modifier.height(Spacing.lg))
        HorizontalDivider(
            color = BorderNeutral,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )
        Spacer(modifier = Modifier.height(Spacing.lg))

        ProfileMenuItem(
            icon = Icons.Filled.Close,
            label = "Log out",
            onClick = { viewModel.logout() },
            iconTint = SentimentNegative,
            labelColor = SentimentNegative,
        )

        Spacer(modifier = Modifier.height(Spacing.xl))
    }
}

@Composable
private fun ProfileTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = ContentPrimary,
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            LendlyLogoMark()
        }
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .padding(bottom = Spacing.lg),
    ) {
        Text(
            text = "Manage",
            style = TitleLarge,
            color = ContentPrimary,
            modifier = Modifier.padding(vertical = Spacing.xs),
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(color = BackgroundCard, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "KB",
                        style = TitleLarge,
                        color = ContentOnSurface,
                    )
                }
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(color = ContentSecondary, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Edit photo",
                        tint = BackgroundScreen,
                        modifier = Modifier.size(12.dp),
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "Kathryn Murphy",
                    style = TitleMedium,
                    color = ContentPrimary,
                )
                Text(
                    text = "@kathrynmurphy",
                    style = CaptionMedium,
                    color = ContentTertiary,
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(InteractiveAccent)
                    .clickable { }
                    .padding(horizontal = Spacing.md, vertical = Spacing.xs),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Edit",
                    style = ButtonLabel,
                    color = ContentPrimary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ProfileSectionLabel(title: String) {
    Text(
        text = title,
        style = CaptionMedium,
        color = ContentTertiary,
        modifier = Modifier
            .padding(horizontal = Spacing.md)
            .padding(bottom = Spacing.xs),
    )
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = ContentOnSurface,
    labelColor: Color = ContentPrimary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = BackgroundCard, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp),
            )
        }

        Text(
            text = label,
            style = Body,
            color = labelColor,
            modifier = Modifier.weight(1f),
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = ContentTertiary,
            modifier = Modifier.size(20.dp),
        )
    }
}