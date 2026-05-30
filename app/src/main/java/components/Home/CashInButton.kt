// CashInButton.kt
package com.lendly.fintech.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.ButtonLabel
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.BaseDark

@Composable
fun CashInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(120.dp)
            .height(48.dp),
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = InteractiveAccent
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = BaseDark
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Cash In",
            style = ButtonLabel,
            color = BaseDark
        )
    }
}