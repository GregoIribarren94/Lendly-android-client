package com.lendly.fintech.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.LendlyTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = CornerFull,
        // primaryContainer = lime green (#7BF179) per Figma; M3 handles disabled alpha automatically
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview(name = "Primary - Enabled")
@Composable
private fun PrimaryButtonEnabledPreview() {
    LendlyTheme {
        Surface {
            PrimaryButton(
                text = "Continuar",
                onClick = {},
            )
        }
    }
}

@Preview(name = "Primary - Disabled")
@Composable
private fun PrimaryButtonDisabledPreview() {
    LendlyTheme {
        Surface {
            PrimaryButton(
                text = "Continuar",
                onClick = {},
                enabled = false,
            )
        }
    }
}

@Preview(name = "Primary - Loading")
@Composable
private fun PrimaryButtonLoadingPreview() {
    LendlyTheme {
        Surface {
            PrimaryButton(
                text = "Continuar",
                onClick = {},
                isLoading = true,
            )
        }
    }
}
