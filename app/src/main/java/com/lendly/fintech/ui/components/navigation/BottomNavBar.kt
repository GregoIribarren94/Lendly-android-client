package com.lendly.fintech.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.lendly.fintech.ui.navigation.Routes
import com.lendly.fintech.ui.theme.BackgroundNeutral
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.LendlyTheme

@Composable
fun BottomNavBar(
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = BackgroundScreen, // blanco plano, sin tinte de elevación
    ) {
        BottomNavItem.all.forEach { item ->
            NavigationBarItem(
                selected = item.route == selectedRoute,
                onClick = { onItemSelected(item) },
                icon = { Icon(painter = painterResource(item.iconRes), contentDescription = item.label) },
                label = { Text(text = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ContentPrimary,
                    selectedTextColor = ContentPrimary,
                    indicatorColor = BackgroundNeutral, // píldora verde suave del item activo
                    unselectedIconColor = ContentTertiary,
                    unselectedTextColor = ContentTertiary,
                ),
            )
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
