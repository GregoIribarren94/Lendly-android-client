package com.lendly.fintech.ui.screens.shop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.components.navigation.LendlyTopBar
import com.lendly.fintech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onProductClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            LendlyTopBar(
                leading = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = ContentPrimary,
                        )
                    }
                },
                title = {
                    Text(text = "Search", style = BodyEmphasis, color = ContentPrimary)
                },
            )
        },
        containerColor = BackgroundScreen,
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Spacing.md),
        ) {

            // ── Campo de búsqueda ────────────────────────────────────────
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                placeholder = {
                    Text("Search for product", style = Body, color = ContentTertiary)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = ContentTertiary,
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = viewModel::clearQuery) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = ContentTertiary,
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = InteractivePrimary,
                    unfocusedBorderColor    = BorderNeutral,
                    focusedContainerColor   = BackgroundScreen,
                    unfocusedContainerColor = BackgroundScreen,
                ),
            )

            Spacer(Modifier.height(Spacing.md))

            // ── Contenido principal ──────────────────────────────────────
            when {
                // Query vacío → mostrar búsquedas recientes
                query.isBlank() -> {
                    RecentSearches(
                        recents = recentSearches,
                        onRecentClick = { viewModel.onQueryChange(it) },
                        onRemove      = { viewModel.removeRecent(it) },
                        onClearAll    = { viewModel.clearAllRecents() },
                    )
                }

                // Sin resultados → EmptyState
                results.isEmpty() -> {
                    EmptyState(query = query)
                }

                // Con resultados → lista filtrada
                else -> {
                    SearchResults(
                        results    = results,
                        onProductClick = { id ->
                            viewModel.saveRecent(query)
                            onProductClick(id)
                        },
                    )
                }
            }
        }
    }
}

// ── Recent Searches ──────────────────────────────────────────────────────────

@Composable
private fun RecentSearches(
    recents: List<String>,
    onRecentClick: (String) -> Unit,
    onRemove: (String) -> Unit,
    onClearAll: () -> Unit,
) {
    if (recents.isEmpty()) return

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "Recent", style = BodyEmphasis, color = ContentPrimary)
        TextButton(onClick = onClearAll) {
            Text(text = "Clear All", style = Caption, color = InteractivePrimary)
        }
    }

    LazyColumn {
        items(items = recents, key = { it }) { recent ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRecentClick(recent) }
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = recent,
                    style = Body,
                    color = ContentPrimary,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                IconButton(
                    onClick = { onRemove(recent) },
                    modifier = Modifier.size(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Remove",
                        tint = ContentTertiary,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
            HorizontalDivider(color = BorderNeutral.copy(alpha = 0.4f))
        }
    }
}

// ── Search Results ───────────────────────────────────────────────────────────

@Composable
private fun SearchResults(
    results: List<Product>,
    onProductClick: (String) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        items(items = results, key = { it.id }) { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick(product.id) }
                    .padding(vertical = Spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = BodyEmphasis,
                        color = ContentPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "${product.brand} · ${product.category}",
                        style = Caption,
                        color = ContentTertiary,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "₱${product.price.toInt()}", style = BodyEmphasis, color = InteractivePrimary)
                    Text(text = "${product.installments} mo",  style = Caption,      color = ContentTertiary)
                }
            }
            HorizontalDivider(color = BorderNeutral.copy(alpha = 0.4f))
        }
    }
}

// ── Empty State ──────────────────────────────────────────────────────────────

@Composable
private fun EmptyState(query: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = ContentTertiary,
        )
        Text(text = "No results for \"$query\"", style = SectionTitle, color = ContentPrimary)
        Text(
            text = "Try a different keyword or browse by category",
            style = Body,
            color = ContentTertiary,
        )
    }
}