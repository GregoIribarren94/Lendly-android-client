package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreatePasswordScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("CreatePassword - TODO")
    }
}
