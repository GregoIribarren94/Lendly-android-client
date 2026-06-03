package com.lendly.fintech.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.data.model.Transaction
import com.lendly.fintech.data.model.TransactionType
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.components.feedback.EmptyState
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundCircleNeutral
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.BalanceTitle
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.IconTintDark
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.SectionTitle
import com.lendly.fintech.ui.theme.SentimentPositive
import com.lendly.fintech.ui.theme.Spacing
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

/** Placeholder visual: la API no expone `fee` (ver mapeo del ticket KAN-57). */
private const val FEE_PLACEHOLDER = "₱100.00"

@Composable
fun TxDetailsScreen(onBack: () -> Unit) {
    val vm: TxDetailsViewModel = hiltViewModel()
    val state by vm.uiState.collectAsState()

    when (val s = state) {
        is TxDetailsUiState.Loading -> TxDetailsStatusScaffold(onBack) {
            CircularProgressIndicator()
        }
        is TxDetailsUiState.Error -> TxDetailsStatusScaffold(onBack) {
            TxDetailsErrorBody(message = s.message, onRetry = vm::load)
        }
        is TxDetailsUiState.NotFound -> TxDetailsStatusScaffold(onBack) {
            EmptyState(message = stringResource(R.string.tx_details_not_found))
        }
        is TxDetailsUiState.Success -> TxDetailsContent(
            transaction = s.transaction,
            onBack = onBack,
            onInfo = {},
            onMore = {},
            onHelp = {},
        )
    }
}

/** Layout principal (stateless) — fiel al SVG `Transaction Details.svg`. */
@Composable
private fun TxDetailsContent(
    transaction: Transaction,
    onBack: () -> Unit,
    onInfo: () -> Unit,
    onMore: () -> Unit,
    onHelp: () -> Unit,
) {
    val company = transaction.company()
    val outflow = transaction.amount < 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // ── Hero (#FCF8F8) — altura intrínseca, crece con el status bar inset ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundCard)
                .statusBarsPadding()
                .heightIn(min = 332.dp)
                .padding(horizontal = Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.sm)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BackCircleButton(onClick = onBack)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onInfo, modifier = Modifier.size(48.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.tx_details_info),
                        tint = ContentOnSurface,
                        modifier = Modifier.size(24.dp),
                    )
                }
                IconButton(onClick = onMore, modifier = Modifier.size(48.dp)) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.tx_details_more),
                        tint = ContentOnSurface,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Spacer(Modifier.height(Spacing.lg))

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(InteractiveAccent),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (outflow) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = IconTintDark,
                    modifier = Modifier.size(32.dp),
                )
            }

            Spacer(Modifier.height(Spacing.xs))

            Text(
                text = if (transaction.type == TransactionType.LOAN_PAYMENT) {
                    stringResource(R.string.tx_details_hero_paid_this_month)
                } else {
                    stringResource(R.string.tx_details_hero_received)
                },
                style = Body,
                color = ContentTertiary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(Spacing.xs))

            Text(
                text = transaction.heroAmount(),
                style = BalanceTitle,
                color = ContentOnSurface,
                textAlign = TextAlign.Center,
            )

            if (company.isNotEmpty()) {
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = stringResource(
                        if (outflow) R.string.tx_details_to else R.string.tx_details_from,
                        company,
                    ),
                    style = Body,
                    color = ContentTertiary,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(Spacing.sm))

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
                    text = if (transaction.type == TransactionType.LOAN_PAYMENT) {
                        stringResource(R.string.tx_details_tag_paid_bills)
                    } else {
                        stringResource(R.string.tx_details_tag_cash_in)
                    },
                    style = Body,
                    color = ContentTertiary,
                )
            }

            Spacer(Modifier.height(Spacing.lg))
        }

        // ── Sección detalle ──────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md),
        ) {
            Spacer(Modifier.height(Spacing.xl))

            Text(
                text = stringResource(R.string.tx_details_section_title),
                style = SectionTitle,
                color = ContentOnSurface,
            )

            Spacer(Modifier.height(Spacing.md))

            DetailRow(label = stringResource(R.string.tx_details_fee), value = FEE_PLACEHOLDER)
            DetailRow(
                label = stringResource(R.string.tx_details_datetime),
                value = transaction.dateTime(),
            )
            DetailRow(
                label = stringResource(R.string.tx_details_tx_number),
                value = transaction.txNumber(),
                isLink = true,
            )

            Spacer(Modifier.height(Spacing.sm))
            HorizontalDivider(color = BorderNeutral)

            Spacer(Modifier.height(Spacing.xl))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.tx_details_help_question),
                    style = Body,
                    color = ContentTertiary,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(R.string.tx_details_help_center),
                    style = Body.copy(textDecoration = TextDecoration.Underline),
                    color = SentimentPositive,
                    modifier = Modifier.clickable(onClick = onHelp),
                )
            }

            Spacer(Modifier.height(Spacing.xxl))
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun BackCircleButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(BackgroundCircleNeutral)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.tx_details_back),
            tint = ContentOnSurface,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String, isLink: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, style = Body, color = ContentTertiary, modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = if (isLink) Body.copy(textDecoration = TextDecoration.Underline) else Body,
            color = if (isLink) SentimentPositive else ContentOnSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )
    }
}

/** Scaffold mínimo para Loading/Error/NotFound: status bar + back, contenido centrado. */
@Composable
private fun TxDetailsStatusScaffold(onBack: () -> Unit, body: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md, vertical = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackCircleButton(onClick = onBack)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center,
        ) {
            body()
        }
    }
}

@Composable
private fun TxDetailsErrorBody(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message, style = Body, color = ContentTertiary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(Spacing.md))
        PrimaryButton(text = stringResource(R.string.history_retry), onClick = onRetry)
    }
}

// ── Display-mapping (privado de esta pantalla) ────────────────────────────────

/** Comercio: lo posterior al "—" del título; fallback a la descripción. */
private fun Transaction.company(): String =
    title.substringAfter("—", "").trim().ifEmpty { description.trim() }

/** Monto del hero: sin signo, 2 decimales, con moneda. Ej: "1,255.00 PHP". */
private fun Transaction.heroAmount(): String =
    String.format(Locale.US, "%,.2f %s", abs(amount), currency)

/** Fecha legible: "Jul 15, 2024 9:12 AM". */
private fun Transaction.dateTime(): String = runCatching {
    OffsetDateTime.parse(date)
        .atZoneSameInstant(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a", Locale.ENGLISH))
}.getOrDefault(date)

/** Número de transacción: `referenceNumber` o `id`, prefijado con "#". */
private fun Transaction.txNumber(): String {
    val raw = referenceNumber ?: id
    return if (raw.startsWith("#")) raw else "#$raw"
}

// ── Previews (sobre el contenido stateless) ───────────────────────────────────

private val previewTx = Transaction(
    id = "tx_001",
    type = TransactionType.LOAN_PAYMENT,
    title = "Loan Payment — Nike Inc.",
    description = "Monthly installment payment",
    amount = -1255.00,
    currency = "PHP",
    status = "COMPLETED",
    date = "2024-07-15T09:12:00Z",
    loanId = "ln_42",
    referenceNumber = "TXN-2024-07-15-001",
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TxDetailsContentPreview() {
    LendlyTheme {
        TxDetailsContent(
            transaction = previewTx,
            onBack = {},
            onInfo = {},
            onMore = {},
            onHelp = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TxDetailsContentNoRefPreview() {
    LendlyTheme {
        TxDetailsContent(
            transaction = previewTx.copy(referenceNumber = null),
            onBack = {},
            onInfo = {},
            onMore = {},
            onHelp = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TxDetailsLoadingPreview() {
    LendlyTheme { TxDetailsStatusScaffold(onBack = {}) { CircularProgressIndicator() } }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TxDetailsErrorPreview() {
    LendlyTheme {
        TxDetailsStatusScaffold(onBack = {}) {
            TxDetailsErrorBody(message = "Sin conexión", onRetry = {})
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TxDetailsNotFoundPreview() {
    LendlyTheme {
        TxDetailsStatusScaffold(onBack = {}) { EmptyState(message = "Transaction not found") }
    }
}
