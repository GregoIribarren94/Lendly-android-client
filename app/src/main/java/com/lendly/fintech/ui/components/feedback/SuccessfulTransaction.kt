package com.lendly.fintech.ui.components.feedback

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.lendly.fintech.ui.components.buttons.FixedBottomBar
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundCircleNeutral
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.IconTintDark
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.SentimentPositive

data class SuccessTxDetail(
    val label: String,
    val value: String,
    val isLink: Boolean = false,
)

@Composable
fun SuccessfulTransaction(
    title: String,
    amount: String,
    details: List<SuccessTxDetail>,
    sectionTitle: String,
    doneButtonText: String,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    tag: String? = null,
    illustration: ImageVector = Icons.Filled.Check,
    needHelpText: String? = null,
    helpLinkText: String? = null,
    onHelp: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    onInfo: (() -> Unit)? = null,
    onMore: (() -> Unit)? = null,
) {
    SuccessfulTransactionSystemBars()

    Scaffold(
        containerColor = BackgroundScreen,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            FixedBottomBar(
                text = doneButtonText,
                onClick = onDone,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(376.dp)
                    .background(BackgroundCard)
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 28.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (onClose != null) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(BackgroundCircleNeutral)
                                .clickable(onClick = onClose),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = ContentPrimary,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(48.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    TopIconButton(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info",
                        onClick = onInfo ?: onHelp,
                    )
                    TopIconButton(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        onClick = onMore,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(InteractiveAccent),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = illustration,
                        contentDescription = null,
                        tint = IconTintDark,
                        modifier = Modifier.size(32.dp),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ContentTertiary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = amount,
                    style = MaterialTheme.typography.displaySmall,
                    color = ContentPrimary,
                    textAlign = TextAlign.Center,
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = ContentTertiary,
                        textAlign = TextAlign.Center,
                    )
                }

                if (tag != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .defaultMinSize(minWidth = 86.dp)
                            .border(1.dp, ContentTertiary, CornerFull)
                            .clip(CornerFull)
                            .padding(horizontal = 18.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.bodyLarge,
                            color = ContentTertiary,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = sectionTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = ContentOnSurface,
                )

                Spacer(modifier = Modifier.height(18.dp))

                details.forEach { detail ->
                    DetailRow(detail = detail)
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                if (onHelp != null && needHelpText != null && helpLinkText != null) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = needHelpText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = ContentTertiary,
                        )
                        Text(
                            text = helpLinkText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                textDecoration = TextDecoration.Underline,
                            ),
                            color = SentimentPositive,
                            modifier = Modifier.clickable(onClick = onHelp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(62.dp))
            }
        }
    }
}

@Composable
@Suppress("DEPRECATION")
private fun SuccessfulTransactionSystemBars() {
    val view = LocalView.current
    DisposableEffect(view) {
        if (view.isInEditMode) {
            onDispose { }
        } else {
            val window = (view.context as? Activity)?.window
            val previousStatusBarColor = window?.statusBarColor
            val previousNavigationBarColor = window?.navigationBarColor

            if (window != null) {
                window.statusBarColor = BackgroundCard.toArgb()
                window.navigationBarColor = BackgroundScreen.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
            }

            onDispose {
                if (window != null) {
                    if (previousStatusBarColor != null) {
                        window.statusBarColor = previousStatusBarColor
                    }
                    if (previousNavigationBarColor != null) {
                        window.navigationBarColor = previousNavigationBarColor
                    }
                }
            }
        }
    }
}

@Composable
private fun TopIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: (() -> Unit)?,
) {
    IconButton(
        onClick = onClick ?: {},
        modifier = Modifier.size(48.dp),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = ContentOnSurface,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun DetailRow(detail: SuccessTxDetail) {
    // Letra un poco más chica que bodyLarge para que el AM/PM de la fecha entre completo.
    val detailStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 40.dp)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = detail.label,
            style = detailStyle,
            color = ContentTertiary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = detail.value,
            style = if (detail.isLink) {
                detailStyle.copy(textDecoration = TextDecoration.Underline)
            } else {
                detailStyle
            },
            color = if (detail.isLink) SentimentPositive else ContentTertiary,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1.4f),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SuccessfulTransactionCashInPreview() {
    LendlyTheme {
        SuccessfulTransaction(
            title = "Added to your account",
            amount = "\$2,500.00",
            details = listOf(
                SuccessTxDetail(label = "Date & Time", value = "May 31, 2026 02:00 PM"),
                SuccessTxDetail(label = "Amount", value = "\$2,500.00"),
                SuccessTxDetail(label = "Transaction No.", value = "A1B2C3D4", isLink = true),
            ),
            sectionTitle = "Transaction Details",
            doneButtonText = "Done",
            onDone = {},
            subtitle = "From GCash",
            tag = "Cash-In",
            illustration = Icons.Filled.Add,
            needHelpText = "Need help?",
            helpLinkText = "Go to Help Center",
            onHelp = {},
            onClose = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SuccessfulTransactionLoanPreview() {
    LendlyTheme {
        SuccessfulTransaction(
            title = "Added to your account",
            amount = "2,000.00 PHP",
            details = listOf(
                SuccessTxDetail(label = "Monthly Fee", value = "₱982.12"),
                SuccessTxDetail(label = "Interest", value = "2.99%"),
                SuccessTxDetail(label = "Installment plan", value = "6 Months"),
                SuccessTxDetail(label = "Date & Time", value = "Jul 15, 2024 9:12 AM"),
                SuccessTxDetail(label = "Transaction Number", value = "#200412312551", isLink = true),
            ),
            sectionTitle = "Transaction Details",
            doneButtonText = "Done",
            onDone = {},
            subtitle = "From Apple Inc.",
            tag = "Loan Amount",
            illustration = Icons.Filled.Add,
            needHelpText = "Need help?",
            helpLinkText = "Go to Help Center",
            onHelp = {},
            onClose = {},
        )
    }
}
