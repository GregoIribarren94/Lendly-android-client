package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.ui.screens.manage.components.ManageMenuItem
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.BalanceTitle
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.BodyEmphasis
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.DisplayTitle
import com.lendly.fintech.ui.theme.FormHelper
import com.lendly.fintech.ui.theme.FormLabel
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.OnboardingSubtitle
import com.lendly.fintech.ui.theme.Spacing

private const val MIN_SCORE = 300
private const val MAX_SCORE = 850

@Composable
fun CreditScoreScreen(
    onBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: CreditScoreViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
            .verticalScroll(rememberScrollState()),
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(start = Spacing.xs, top = Spacing.xs),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left_alt),
                contentDescription = stringResource(R.string.credit_score_back_content_description),
                tint = ContentPrimary,
            )
        }

        Text(
            text = stringResource(R.string.credit_score_title),
            style = Headline,
            color = ContentOnSurface,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )

        Spacer(Modifier.height(Spacing.md))

        CreditScoreCard(
            score = state.score,
            labelRes = state.labelRes,
            modifier = Modifier.padding(horizontal = Spacing.md),
        )

        Spacer(Modifier.height(Spacing.lg))

        Text(
            text = stringResource(R.string.credit_score_section_general),
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
            label = stringResource(R.string.credit_score_row_account_details),
            onClick = onNavigateToProfile,
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_markunread_mailbox,
            label = stringResource(R.string.credit_score_row_receiving),
            onClick = {},
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_event,
            label = stringResource(R.string.credit_score_row_scheduled_pay),
            onClick = {},
        )
        ManageMenuItem(
            iconRes = R.drawable.ic_settings,
            label = stringResource(R.string.credit_score_row_settings),
            onClick = {},
        )

        Spacer(Modifier.height(Spacing.xl))
    }
}

@Composable
private fun CreditScoreCard(
    score: Int?,
    @androidx.annotation.StringRes labelRes: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(BackgroundCard)
            .padding(horizontal = Spacing.md, vertical = Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScoreGauge(score = score ?: MIN_SCORE)

        Spacer(Modifier.height(Spacing.xs))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.credit_score_min),
                style = Body,
                color = ContentSecondary,
            )
            Text(
                text = stringResource(R.string.credit_score_max),
                style = Body,
                color = ContentSecondary,
            )
        }

        Spacer(Modifier.height(Spacing.md))

        Text(
            text = score?.toString() ?: stringResource(R.string.credit_score_value_placeholder),
            style = DisplayTitle,
            color = ContentOnSurface,
        )

        Spacer(Modifier.height(Spacing.xs))

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.credit_score_label_prefix))
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(labelRes))
                }
            },
            style = OnboardingSubtitle,
            color = ContentSecondary,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(Spacing.md))

        HorizontalDivider(
            color = BorderNeutral,
            thickness = 1.dp,
        )

        Spacer(Modifier.height(Spacing.md))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Text(
                text = stringResource(R.string.credit_score_what_is_title),
                style = BodyEmphasis,
                color = ContentPrimary,
            )
            Text(
                text = stringResource(R.string.credit_score_what_is_body),
                style = FormHelper,
                color = ContentTertiary,
            )
        }
    }
}

@Composable
private fun ScoreGauge(
    score: Int,
    modifier: Modifier = Modifier,
) {
    val t = ((score - MIN_SCORE).toFloat() / (MAX_SCORE - MIN_SCORE)).coerceIn(0f, 1f)
    val angleFromRightDeg = 180f - t * 180f
    val needleNaturalAngleDeg = 45f
    val rotationDeg = needleNaturalAngleDeg - angleFromRightDeg

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(155.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            painter = painterResource(R.drawable.illu_ellipse_12),
            contentDescription = null,
            modifier = Modifier
                .size(width = 282.dp, height = 141.dp)
                .align(Alignment.BottomCenter),
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(56.dp)
                .offset(x = 28.dp)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0f, 1f)
                    rotationZ = rotationDeg
                },
        ) {
            Image(
                painter = painterResource(R.drawable.illu_line_1),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
            )
        }

        Image(
            painter = painterResource(R.drawable.illu_ellipse_14),
            contentDescription = null,
            modifier = Modifier
                .size(11.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-5.5).dp),
        )
    }
}

@Preview(name = "CreditScore", showBackground = true, heightDp = 900)
@Composable
private fun CreditScoreScreenPreview() {
    LendlyTheme {
        CreditScoreScreen(onBack = {}, onNavigateToProfile = {})
    }
}
