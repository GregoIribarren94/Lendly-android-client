package com.lendly.fintech.ui.components.navigation

import androidx.annotation.DrawableRes
import com.lendly.fintech.R
import com.lendly.fintech.ui.navigation.Routes

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int,
) {
    data object Home : BottomNavItem(Routes.HOME, "Home", R.drawable.ic_home)
    data object Loan : BottomNavItem(Routes.LOAN, "Loan", R.drawable.ic_credit_card)
    data object Shop : BottomNavItem(Routes.SHOP, "Shop", R.drawable.ic_shopping_bag)
    data object History : BottomNavItem(Routes.HISTORY, "History", R.drawable.ic_update)
    data object Manage : BottomNavItem(Routes.MANAGE, "Manage", R.drawable.ic_grid_view)

    companion object {
        val all = listOf(Home, Loan, Shop, History, Manage)
    }
}
