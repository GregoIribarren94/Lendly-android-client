package com.lendly.fintech.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.ContentLink
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

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
) {
    Scaffold(
        containerColor = BackgroundScreen,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            PrimaryButton(
                text = doneButtonText,
                onClick = onDone,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md),
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
            // ── HERO ────────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundCard)
                    .statusBarsPadding()
                    .padding(top = Spacing.md, bottom = Spacing.xl),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (onClose != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = Spacing.md, bottom = Spacing.lg),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable(onClick = onClose),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(Spacing.xxl))
                }

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = illustration,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp),
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.sm))

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ContentTertiary,
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = amount,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = ContentTertiary,
                    )
                }

                if (tag != null) {
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Box(
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colorScheme.outline, CornerFull)
                            .clip(CornerFull)
                            .padding(horizontal = Spacing.md, vertical = Spacing.xs),
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.bodyLarge,
                            color = ContentTertiary,
                        )
                    }
                }
            }

            // ── BODY ────────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg),
            ) {
                Spacer(modifier = Modifier.height(Spacing.lg))

                Text(
                    text = sectionTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                details.forEach { detail ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = detail.label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = ContentTertiary,
                            modifier = Modifier.weight(1f),
                        )
                        if (detail.isLink) {
                            Text(
                                text = detail.value,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    textDecoration = TextDecoration.Underline,
                                ),
                                color = ContentLink,
                            )
                        } else {
                            Text(
                                text = detail.value,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Spacing.sm))
                }

                Spacer(modifier = Modifier.height(Spacing.md))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                if (onHelp != null && needHelpText != null && helpLinkText != null) {
                    Spacer(modifier = Modifier.height(Spacing.lg))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = needHelpText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = ContentTertiary,
                        )
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        Text(
                            text = helpLinkText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textDecoration = TextDecoration.Underline,
                            ),
                            color = ContentLink,
                            modifier = Modifier.clickable(onClick = onHelp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.xxl))
            }
        }
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
                SuccessTxDetail(label = "Transaction No.", value = "A1B2C3D4", isLink = true),
            ),
            sectionTitle = "Transaction Details",
            doneButtonText = "Done",
            onDone = {},
            subtitle = "From GCash",
            tag = "Cash-In",
            illustration = Icons.Filled.Check,
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
            title = "Loan request submitted",
            amount = "\$5,000.00",
            details = listOf(
                SuccessTxDetail(label = "Date & Time", value = "May 31, 2026 02:00 PM"),
                SuccessTxDetail(label = "Loan ID", value = "LN-00042"),
            ),
            sectionTitle = "Transaction Details",
            doneButtonText = "Done",
            onDone = {},
        )
    }
}
