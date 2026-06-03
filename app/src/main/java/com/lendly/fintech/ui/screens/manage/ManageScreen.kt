package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.navigation.MainTopBar
import com.lendly.fintech.ui.screens.manage.components.ManageMenuItem
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.BaseLight
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.ButtonLabel
import com.lendly.fintech.ui.theme.ContentAmount
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.FormHelper
import com.lendly.fintech.ui.theme.FormLabel
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.Spacing
import com.lendly.fintech.ui.theme.TitleMedium

@Composable
fun ManageScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToCreditScore: () -> Unit,
    viewModel: ManageViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
            .verticalScroll(rememberScrollState()),
    ) {
        MainTopBar()

        ManageHeader()

        ProfileCard(
            profileInitials = state.profileInitials,
            profileImageUrl = state.profileImageUrl,
        )

        Spacer(Modifier.height(Spacing.lg))

        Text(
            text = "General",
            style = FormLabel,
            color = ContentTertiary,
            modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
        )
        HorizontalDivider(
            color = BorderNeutral,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )

        ManageMenuItem(
            iconRes = R.drawable.ic_manage_accounts,
            label = "Account details",
            onClick = onNavigateToProfile,
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_markunread_mailbox,
            label = "Receiving by email or phone",
            onClick = {},
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_event,
            label = "Scheduled pay",
            onClick = {},
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_readiness_score,
            label = "Credit score",
            onClick = onNavigateToCreditScore,
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_settings,
            label = "Settings",
            onClick = {},
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_description,
            label = "Terms and Conditions",
            onClick = {},
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_question_mark,
            label = "Help",
            onClick = {},
        )

        Spacer(Modifier.height(Spacing.lg))
        HorizontalDivider(
            color = BorderNeutral,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )
        Spacer(Modifier.height(Spacing.sm))

        ManageMenuItem(
            iconRes = R.drawable.ic_move_item,
            label = "Log Out",
            onClick = { viewModel.logout() },
        )

        Spacer(Modifier.height(Spacing.xl))
    }
}

@Composable
private fun ManageHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md),
    ) {
        Text(
            text = "Manage",
            style = Headline,
            color = ContentOnSurface,
        )
        Spacer(Modifier.height(Spacing.md))
        Text(
            text = "Currently using as",
            style = Body,
            color = ContentTertiary,
        )
        Spacer(Modifier.height(Spacing.xs))
        HorizontalDivider(color = BorderNeutral, thickness = 1.dp)
    }
}

@Composable
private fun ProfileCard(
    profileInitials: String,
    profileImageUrl: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            if (profileImageUrl != null) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_avatar_placeholder),
                    error = painterResource(R.drawable.ic_avatar_placeholder),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = BackgroundCard, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = profileInitials.ifEmpty { "—" },
                        style = TitleMedium,
                        color = ContentOnSurface,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(color = ContentSecondary, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_a_photo),
                    contentDescription = "Edit photo",
                    tint = BaseLight,
                    modifier = Modifier.size(12.dp),
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = "Account details",
                style = Body,
                color = ContentPrimary,
            )
            Text(
                text = "Your personal Account",
                style = FormHelper,
                color = ContentTertiary,
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(InteractiveAccent)
                .clickable {}
                .padding(horizontal = Spacing.md, vertical = Spacing.xs),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Edit",
                style = ButtonLabel,
                color = ContentAmount,
            )
        }
    }
}
