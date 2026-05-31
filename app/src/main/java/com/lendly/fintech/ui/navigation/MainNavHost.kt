package com.lendly.fintech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lendly.fintech.ui.screens.history.*
import com.lendly.fintech.ui.screens.home.HomeScreen
import com.lendly.fintech.ui.screens.loan.*
import com.lendly.fintech.ui.screens.manage.*
import com.lendly.fintech.ui.screens.shop.*
import com.lendly.fintech.ui.screens.shop.*

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SHOP,
        modifier = modifier,
    ) {
        // HOME TAB
        composable(Routes.HOME) {
            HomeScreen()
        }

        // LOAN TAB
        composable(Routes.LOAN) {
            LoanScreen(
                onNavigateToCashIn = { navController.navigate(Routes.CASH_IN) },
                onNavigateToLoanInfo = { navController.navigate(Routes.LOAN_INFO) },
                onNavigateToActiveLoan = { navController.navigate(Routes.ACTIVE_LOAN) },
            )
        }
        composable(Routes.CASH_IN) {
            CashInScreen(
                onSelectOtc = { navController.navigate(Routes.OTC_CASH_IN) },
                onSelectOnline = { navController.navigate(Routes.ONLINE_CASH_IN) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.OTC_CASH_IN) {
            OtcCashInScreen(
                onPartnerSelected = { partnerId ->
                    navController.navigate(Routes.otcCashInForm(partnerId))
                },
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Routes.OTC_CASH_IN_FORM,
            arguments = listOf(navArgument(Routes.ARG_PARTNER_ID) { type = NavType.StringType }),
        ) {
            OtcCashInFormScreen(
                onSuccess = { referenceCode, amount, method ->
                    navController.navigate(Routes.successTxWithRef(referenceCode, amount, method))
                },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.ONLINE_CASH_IN) {
            OnlineCashInScreen(
                onMethodSelected = { methodId ->
                    navController.navigate(Routes.onlineCashInForm(methodId))
                },
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Routes.ONLINE_CASH_IN_FORM,
            arguments = listOf(navArgument(Routes.ARG_METHOD_ID) { type = NavType.StringType }),
        ) {
            OnlineCashInFormScreen(
                onSuccess = { amount, method ->
                    navController.navigate(Routes.successTxOnline(amount, method))
                },
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = "${Routes.SUCCESS_TX}?${Routes.ARG_REF_CODE}={${Routes.ARG_REF_CODE}}&${Routes.ARG_AMOUNT}={${Routes.ARG_AMOUNT}}&${Routes.ARG_METHOD}={${Routes.ARG_METHOD}}",
            arguments = listOf(
                navArgument(Routes.ARG_REF_CODE) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(Routes.ARG_AMOUNT) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(Routes.ARG_METHOD) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
            ),
        ) { backStackEntry ->
            val referenceCode = backStackEntry.arguments?.getString(Routes.ARG_REF_CODE)
            val amount = backStackEntry.arguments?.getString(Routes.ARG_AMOUNT).orEmpty()
            val method = backStackEntry.arguments?.getString(Routes.ARG_METHOD).orEmpty()
            SuccessTxScreen(
                referenceCode = referenceCode,
                amount = amount,
                method = method,
                onDone = {
                    navController.popBackStack(Routes.LOAN, inclusive = false)
                },
            )
        }
        composable(Routes.LOAN_INFO) {
            LoanInfoScreen(
                onApply = { navController.navigate(Routes.LOAN_FORM) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.LOAN_FORM) {
            LoanFormScreen(
                onSubmit = { navController.navigate(Routes.ACTIVE_LOAN) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.ACTIVE_LOAN) {
            ActiveLoanScreen(
                onBack = { navController.popBackStack() },
            )
        }

        // SHOP TAB
        composable(Routes.SHOP) {
            ShopScreen(
                onSearch = { navController.navigate(Routes.SEARCH) },
                onFilter = { navController.navigate(Routes.FILTER) },
                onProductClick = { id -> navController.navigate(Routes.product(id)) },
            )
        }
        composable(Routes.SEARCH) {
            SearchScreen(
                onProductClick = { id -> navController.navigate(Routes.product(id)) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.FILTER) {
            FilterScreen(
                onApply = { navController.popBackStack() },
                onBack  = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.PRODUCT,
            arguments = listOf(navArgument(Routes.ARG_ID) { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Routes.ARG_ID).orEmpty()
            ProductScreen(
                productId = id,
                onBack = { navController.popBackStack() },
            )
        }

        // HISTORY TAB
        composable(Routes.HISTORY) {
            HistoryScreen(
                onTxClick = { id -> navController.navigate(Routes.txDetails(id)) },
            )
        }
        composable(
            route = Routes.TX_DETAILS,
            arguments = listOf(navArgument(Routes.ARG_ID) { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Routes.ARG_ID).orEmpty()
            TxDetailsScreen(
                txId = id,
                onBack = { navController.popBackStack() },
            )
        }

        // MANAGE TAB
        composable(Routes.MANAGE) {
            ManageScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToCreditScore = { navController.navigate(Routes.CREDIT_SCORE) },
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onDetailClick = { id -> navController.navigate(Routes.profileDetail(id)) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Routes.PROFILE_DETAIL,
            arguments = listOf(navArgument(Routes.ARG_ID) { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Routes.ARG_ID).orEmpty()
            ProfileDetailScreen(
                detailId = id,
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.CREDIT_SCORE) {
            CreditScoreScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
