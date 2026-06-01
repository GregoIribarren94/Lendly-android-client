package com.lendly.fintech.ui.components.feedback

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.LendlyTheme

data class SuccessTxDetail(
    val label: String,
    val value: String,
    val isLink: Boolean = false,
)

private val SuccessHeroBackground = Color(0xFFFCF8F8)
private val SuccessCloseBackground = Color(0xFFE5E2E1)
private val SuccessAccent = Color(0xFF7BF179)
private val SuccessIconTint = Color(0xFF1C1B1F)
private val SuccessPrimaryText = Color(0xFF122300)
private val SuccessSectionText = Color(0xFF171D1E)
private val SuccessSecondaryText = Color(0xFF6A6C6A)
private val SuccessLinkText = Color(0xFF4C662B)
private val SuccessButtonText = Color(0xFF102000)

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
            Button(
                onClick = onDone,
                shape = CornerFull,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuccessAccent,
                    contentColor = SuccessButtonText,
                ),
                contentPadding = PaddingValues(horizontal = 24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(48.dp),
            ) {
                Text(
                    text = doneButtonText,
                    style = MaterialTheme.typography.labelLarge,
                    color = SuccessButtonText,
                )
            }
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
                    .background(SuccessHeroBackground)
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
                                .background(SuccessCloseBackground)
                                .clickable(onClick = onClose),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = SuccessPrimaryText,
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
                        .background(SuccessAccent),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = illustration,
                        contentDescription = null,
                        tint = SuccessIconTint,
                        modifier = Modifier.size(32.dp),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SuccessSecondaryText,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = amount,
                    style = MaterialTheme.typography.displaySmall,
                    color = SuccessPrimaryText,
                    textAlign = TextAlign.Center,
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = SuccessSecondaryText,
                        textAlign = TextAlign.Center,
                    )
                }

                if (tag != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .defaultMinSize(minWidth = 86.dp)
                            .border(1.dp, SuccessSecondaryText, CornerFull)
                            .clip(CornerFull)
                            .padding(horizontal = 18.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.bodyLarge,
                            color = SuccessSecondaryText,
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
                    color = SuccessSectionText,
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
                            color = SuccessSecondaryText,
                        )
                        Text(
                            text = helpLinkText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                textDecoration = TextDecoration.Underline,
                            ),
                            color = SuccessLinkText,
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
                window.statusBarColor = SuccessHeroBackground.toArgb()
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
            tint = SuccessSectionText,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun DetailRow(detail: SuccessTxDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = detail.label,
            style = MaterialTheme.typography.bodyLarge,
            color = SuccessSecondaryText,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = detail.value,
            style = if (detail.isLink) {
                MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
            } else {
                MaterialTheme.typography.bodyLarge
            },
            color = if (detail.isLink) SuccessLinkText else SuccessSecondaryText,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
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
            title = "Loan request submitted",
            amount = "\$5,000.00",
            details = listOf(
                SuccessTxDetail(label = "Date & Time", value = "May 31, 2026 02:00 PM"),
                SuccessTxDetail(label = "Amount", value = "\$5,000.00"),
                SuccessTxDetail(label = "Loan ID", value = "LN-00042"),
            ),
            sectionTitle = "Transaction Details",
            doneButtonText = "Done",
            onDone = {},
        )
    }
}
