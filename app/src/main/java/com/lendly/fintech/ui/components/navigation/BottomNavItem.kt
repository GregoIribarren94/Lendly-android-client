package com.lendly.fintech.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.lendly.fintech.ui.navigation.Routes

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    data object Home : BottomNavItem(Routes.HOME, "Home", Icons.Default.Home)
    data object Loans : BottomNavItem(Routes.LOANS, "Loans", Icons.Default.CreditCard)
    data object Shop : BottomNavItem(Routes.SHOP, "Shop", Icons.Default.ShoppingCart)
    data object History : BottomNavItem(Routes.HISTORY, "History", Icons.Default.DateRange)
    data object Manage : BottomNavItem(Routes.MANAGE, "Manage", Icons.Default.Settings)

    companion object {
        val all = listOf(Home, Loans, Shop, History, Manage)
    }
}
