package com.lendly.fintech.ui.screens.loan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoanScreen(
    onNavigateToCashIn: () -> Unit,
    onNavigateToLoanInfo: () -> Unit,
    onNavigateToActiveLoan: () -> Unit,
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loan - TODO")
    }
}