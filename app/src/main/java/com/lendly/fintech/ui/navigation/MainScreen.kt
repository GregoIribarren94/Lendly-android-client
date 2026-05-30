package com.lendly.fintech.ui.navigation

import com.lendly.fintech.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private data class BottomTab(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int,
)

private val bottomTabs = listOf(
    BottomTab(Routes.HOME, "Home", R.drawable.ic_home),
    BottomTab(Routes.LOAN, "Loan", R.drawable.ic_credit_card),
    BottomTab(Routes.SHOP, "Shop", R.drawable.ic_shopping_bag),
    BottomTab(Routes.HISTORY, "History", R.drawable.ic_update),
    BottomTab(Routes.MANAGE, "Manage", R.drawable.ic_grid_view),
)

@Composable
fun MainScreen() {
    val nestedNavController = rememberNavController()
    val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomTabs.forEach { tab ->
                    val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            nestedNavController.navigate(tab.route) {
                                // Mantiene el back stack interno de cada tab
                                popUpTo(nestedNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(painterResource(tab.icon), contentDescription = tab.label) },
                        label = { Text(tab.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        MainNavHost(
            navController = nestedNavController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}