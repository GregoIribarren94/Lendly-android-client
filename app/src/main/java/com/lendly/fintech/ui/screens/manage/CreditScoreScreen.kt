package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lendly.fintech.ui.components.navigation.AppTopBar

@Composable
fun CreditScoreScreen(
    onBack: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Credit Score", onBackClick = onBack)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text("CreditScore - TODO")
        }
    }
}
