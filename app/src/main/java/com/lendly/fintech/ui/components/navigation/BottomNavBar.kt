package com.lendly.fintech.ui.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.navigation.Routes
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.LendlyTheme

@Composable
fun BottomNavBar(
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(thickness = 1.dp, color = BorderNeutral)
        NavigationBar(
            containerColor = BackgroundScreen,
            tonalElevation = 0.dp,
        ) {
            BottomNavItem.all.forEach { item ->
                NavigationBarItem(
                    selected = item.route == selectedRoute,
                    onClick = { onItemSelected(item) },
                    icon = { Icon(painter = painterResource(item.iconRes), contentDescription = item.label) },
                    label = { Text(text = item.label) },
                )
            }
        }
    }
}

@Preview(name = "BottomNav - Home selected")
@Composable
private fun BottomNavBarPreviewHome() {
    LendlyTheme {
        Surface {
            BottomNavBar(
                selectedRoute = Routes.HOME,
                onItemSelected = {},
            )
        }
    }
}

@Preview(name = "BottomNav - Shop selected")
@Composable
private fun BottomNavBarPreviewShop() {
    LendlyTheme {
        Surface {
            BottomNavBar(
                selectedRoute = Routes.SHOP,
                onItemSelected = {},
            )
        }
    }
}
