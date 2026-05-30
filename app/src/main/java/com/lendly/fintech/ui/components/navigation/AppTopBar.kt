package com.lendly.fintech.ui.components.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lendly.fintech.ui.theme.LendlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        },
        modifier = modifier,
        navigationIcon = if (onBackClick != null) {
            {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else {
            {}
        },
        actions = actions
    )
}

@Preview(name = "TopBar - Sin back")
@Composable
private fun AppTopBarPreviewWithoutBack() {
    LendlyTheme {
        Surface {
            AppTopBar(title = "Screen Title")
        }
    }
}

@Preview(name = "TopBar - Con back")
@Composable
private fun AppTopBarPreviewWithBack() {
    LendlyTheme {
        Surface {
            AppTopBar(
                title = "Screen Title",
                onBackClick = {}
            )
        }
    }
}

@Preview(name = "TopBar - Con actions")
@Composable
private fun AppTopBarPreviewWithActions() {
    LendlyTheme {
        Surface {
            AppTopBar(
                title = "Screen Title",
                onBackClick = {},
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                }
            )
        }
    }
}
