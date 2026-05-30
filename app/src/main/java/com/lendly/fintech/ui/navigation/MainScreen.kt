package com.lendly.fintech.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lendly.fintech.ui.components.navigation.BottomNavBar

@Composable
fun MainScreen() {
    val nestedNavController = rememberNavController()
    val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedRoute = navBackStackEntry?.destination?.route.orEmpty(),
                onItemSelected = { item ->
                    nestedNavController.navigate(item.route) {
                        // Mantiene el back stack interno de cada tab
                        popUpTo(nestedNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { innerPadding ->
        MainNavHost(
            navController = nestedNavController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
