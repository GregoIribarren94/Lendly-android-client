package com.lendly.fintech.ui.screens.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lendly.fintech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onApply: () -> Unit,
    onBack: () -> Unit,
    viewModel: FilterViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Filter", style = BodyEmphasis, color = ContentPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = ContentPrimary,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BackgroundScreen,
                ),
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md, vertical = Spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                // Reset Filter
                OutlinedButton(
                    onClick = { viewModel.reset() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp,
                    ),
                ) {
                    Text(
                        text = "Reset Filter",
                        style = BodyEmphasis,
                        color = ContentPrimary,
                        fontSize = 14.sp,
                    )
                }

                // Apply
                Button(
                    onClick = {
                        viewModel.apply()
                        onApply()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InteractiveAccent,
                        contentColor   = ContentPrimary,
                    ),
                ) {
                    Text(
                        text = "Apply",
                        style = BodyEmphasis,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        },
        containerColor = BackgroundScreen,
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Spacing.md)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            Spacer(Modifier.height(Spacing.xs))

            // ── Brands ───────────────────────────────────────────────────
            FilterSection(title = "Brands") {
                ChipGroup(
                    options   = listOf("All", "Nike", "Adidas", "Puma", "Jordan"),
                    selected  = state.selectedBrand,
                    onSelect  = { viewModel.selectBrand(it) },
                )
            }

            // ── Gender ───────────────────────────────────────────────────
            FilterSection(title = "Gender") {
                ChipGroup(
                    options   = listOf("All", "Men", "Women"),
                    selected  = state.selectedGender,
                    onSelect  = { viewModel.selectGender(it) },
                )
            }

            // ── Sort By ──────────────────────────────────────────────────
            FilterSection(title = "Sort by") {
                ChipGroup(
                    options   = listOf("Most Recent", "Popular", "Low Interest"),
                    selected  = state.selectedSort,
                    onSelect  = { viewModel.selectSort(it) },
                )
            }

            // ── Price Range ──────────────────────────────────────────────
            FilterSection(title = "Price Range") {
                ChipGroup(
                    options   = listOf("All", "\$500 - \$1000", "\$1000 - \$5000"),
                    selected  = state.selectedPriceRange,
                    onSelect  = { viewModel.selectPriceRange(it) },
                )
            }

            Spacer(Modifier.height(Spacing.sm))
        }
    }
}

// ── Filter Section ────────────────────────────────────────────────────────────

@Composable
private fun FilterSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = ContentPrimary,
            letterSpacing = 0.15.sp,
        )
        content()
    }
}

// ── Chip Group ────────────────────────────────────────────────────────────────

@Composable
private fun ChipGroup(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    // Wrap chips en rows manualmente para respetar el diseño
    val rows = mutableListOf<List<String>>()
    var currentRow = mutableListOf<String>()
    options.forEach { option ->
        currentRow.add(option)
        if (currentRow.size == 4) {
            rows.add(currentRow.toList())
            currentRow = mutableListOf()
        }
    }
    if (currentRow.isNotEmpty()) rows.add(currentRow.toList())

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                row.forEach { option ->
                    FilterChip(
                        label    = option,
                        selected = option == selected,
                        onClick  = { onSelect(option) },
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val bgColor     = if (selected) InteractiveAccent else BackgroundScreen
    val borderColor = if (selected) InteractiveAccent else Color(0xFF6A6C6A)
    val textColor   = if (selected) ContentPrimary    else ContentSecondary

    Box(
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .then(
                if (selected) Modifier.padding(0.dp)
                else Modifier
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text       = label,
            fontSize   = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color      = textColor,
            letterSpacing = 0.1.sp,
        )
    }
}