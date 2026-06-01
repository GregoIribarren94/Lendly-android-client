package com.lendly.fintech.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.data.model.Transaction
import com.lendly.fintech.data.model.TransactionType
import com.lendly.fintech.ui.components.brand.LendlyLogoMark
import com.lendly.fintech.ui.components.feedback.EmptyState
import com.lendly.fintech.ui.screens.history.components.HistoryTransactionRow
import com.lendly.fintech.ui.theme.*
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun HistoryScreen(onTxClick: (String) -> Unit) {
    val vm: HistoryViewModel = hiltViewModel()
    val state by vm.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen),
        contentPadding = PaddingValues(bottom = Spacing.md),
    ) {
        item { HistoryTopBar() }
        item {
            Text(
                text = stringResource(R.string.history_title),
                style = TitleLarge,
                color = ContentPrimary,
                modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
            )
        }

        when (val s = state) {
            is HistoryUiState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = InteractiveAccent)
                    }
                }
            }

            is HistoryUiState.Error -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.lg),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    ) {
                        Text(text = s.message, style = Body, color = ContentTertiary)
                        Button(
                            onClick = vm::load,
                            colors = ButtonDefaults.buttonColors(containerColor = InteractivePrimary),
                        ) {
                            Text(stringResource(R.string.history_retry))
                        }
                    }
                }
            }

            is HistoryUiState.Success -> {
                item {
                    HistorySearchBar(
                        query = s.searchQuery,
                        onQueryChange = vm::onSearchQueryChanged,
                        modifier = Modifier
                            .padding(horizontal = Spacing.md)
                            .padding(bottom = Spacing.xs),
                    )
                }
                item {
                    FilterChipsRow(
                        selectedFilter = s.selectedFilter,
                        onFilterSelected = vm::onFilterSelected,
                        modifier = Modifier.padding(vertical = Spacing.xs),
                    )
                }
                if (s.sections.isEmpty()) {
                    item {
                        EmptyState(
                            message = stringResource(R.string.history_empty_message),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                        )
                    }
                } else {
                    s.sections.forEach { section ->
                        item {
                            Text(
                                text = section.header,
                                style = CaptionMedium,
                                color = ContentTertiary,
                                modifier = Modifier
                                    .padding(horizontal = Spacing.md)
                                    .padding(top = Spacing.md, bottom = Spacing.xs),
                            )
                        }
                        items(section.transactions, key = { it.id }) { tx ->
                            HistoryTransactionRow(
                                icon = tx.toDisplayIcon(),
                                time = tx.toDisplayTime(),
                                title = tx.toDisplayTitle(),
                                company = tx.toDisplayCompany(),
                                amount = tx.toDisplayAmount(),
                                onClick = { onTxClick(tx.id) },
                                modifier = Modifier.padding(
                                    horizontal = Spacing.md,
                                    vertical = Spacing.xs,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = null,
                tint = ContentPrimary,
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            LendlyLogoMark()
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = null,
                tint = ContentPrimary,
            )
        }
    }
}

@Composable
private fun HistorySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(R.string.history_search_placeholder),
                style = Body,
                color = ContentTertiary,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = ContentTertiary,
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = BorderNeutral,
            focusedBorderColor = InteractivePrimary,
            unfocusedContainerColor = BackgroundScreen,
            focusedContainerColor = BackgroundScreen,
        ),
    )
}

@Composable
private fun FilterChipsRow(
    selectedFilter: HistoryFilter,
    onFilterSelected: (HistoryFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    val chips = listOf(
        HistoryFilter.ALL      to stringResource(R.string.history_filter_all),
        HistoryFilter.LOANS    to stringResource(R.string.history_filter_loans),
        HistoryFilter.PAYMENTS to stringResource(R.string.history_filter_payments),
        HistoryFilter.CASH_IN  to stringResource(R.string.history_filter_cash_in),
    )
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        items(chips) { (filter, label) ->
            val isSelected = filter == selectedFilter
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) InteractiveAccent else BackgroundScreen)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) InteractiveAccent else BorderNeutral,
                        shape = RoundedCornerShape(20.dp),
                    )
                    .clickable { onFilterSelected(filter) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = CaptionMedium,
                    color = if (isSelected) ContentPrimary else ContentTertiary,
                )
            }
        }
    }
}

// ── Display-mapping extensions (private — solo las usa esta pantalla) ──────────

private fun Transaction.toDisplayIcon(): ImageVector = when (type) {
    TransactionType.LOAN_PAYMENT      -> Icons.Default.ArrowUpward
    TransactionType.CASH_IN           -> Icons.Default.Add
    TransactionType.LOAN_DISBURSEMENT -> Icons.Default.Check
    null                              -> Icons.Default.SwapHoriz
}

private fun Transaction.toDisplayTime(): String = runCatching {
    OffsetDateTime.parse(date)
        .atZoneSameInstant(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}.getOrDefault("")

/** Acción, sin el comercio: "Loan Payment". */
private fun Transaction.toDisplayTitle(): String = title.substringBefore("—").trim()

/** Comercio, después del guión: "Nike Inc." ("" si no hay). */
private fun Transaction.toDisplayCompany(): String = title.substringAfter("—", "").trim()

/**
 * Monto fiel al Figma: sin signo (la dirección la indica el ícono),
 * con separador de miles y moneda. Ej: "1,000 PHP".
 */
private fun Transaction.toDisplayAmount(): String = "%,.0f %s".format(abs(amount), currency)
