package com.lendly.fintech.ui.screens.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.data.model.OnlineMethod
import com.lendly.fintech.data.model.OnlineMethodType
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.ui.components.navigation.AppTopBar
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun OnlineCashInScreen(
    onMethodSelected: (String) -> Unit,
    onBack: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    val entries = OnlineMethod.entries.map { method ->
        method to stringResource(method.nameRes)
    }
    val filteredEntries = entries.filter { (_, name) ->
        searchQuery.isBlank() || name.contains(searchQuery, ignoreCase = true)
    }
    val filteredBanks = filteredEntries.filter { (method, _) -> method.type == OnlineMethodType.BANK }
    val filteredEwallets = filteredEntries.filter { (method, _) ->
        method.type == OnlineMethodType.E_WALLET
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.online_cash_in_top_bar_title),
                onBackClick = onBack,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Spacing.lg)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            AppTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = "",
                placeholder = stringResource(R.string.online_cash_in_search_placeholder),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                modifier = Modifier.padding(top = Spacing.md),
            )

            OnlineMethodSection(
                title = stringResource(R.string.online_cash_in_section_banks),
                entries = filteredBanks,
                onMethodSelected = onMethodSelected,
            )

            OnlineMethodSection(
                title = stringResource(R.string.online_cash_in_section_ewallets),
                entries = filteredEwallets,
                onMethodSelected = onMethodSelected,
            )
        }
    }
}

@Composable
private fun OnlineMethodSection(
    title: String,
    entries: List<Pair<OnlineMethod, String>>,
    onMethodSelected: (String) -> Unit,
) {
    if (entries.isEmpty()) return

    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = Spacing.sm),
    )
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        entries.forEachIndexed { index, (method, name) ->
            OnlineMethodRow(
                method = method,
                displayName = name,
                onClick = { onMethodSelected(method.name) },
            )
            if (index < entries.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 80.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }
        }
    }
}

@Composable
private fun OnlineMethodRow(
    method: OnlineMethod,
    displayName: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.md, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(method.logoRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp),
            )
        }
        Text(
            text = displayName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
