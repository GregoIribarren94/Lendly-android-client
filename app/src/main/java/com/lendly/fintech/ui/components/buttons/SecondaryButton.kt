package com.lendly.fintech.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.LendlyTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = CornerFull,
        colors = OutlinedButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        border = OutlinedButtonDefaults.outlinedButtonBorder(),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(name = "Secondary - Enabled")
@Composable
private fun SecondaryButtonEnabledPreview() {
    LendlyTheme {
        Surface {
            SecondaryButton(
                text = "Cancelar",
                onClick = {},
            )
        }
    }
}

@Preview(name = "Secondary - Disabled")
@Composable
private fun SecondaryButtonDisabledPreview() {
    LendlyTheme {
        Surface {
            SecondaryButton(
                text = "Cancelar",
                onClick = {},
                enabled = false,
            )
        }
    }
}
