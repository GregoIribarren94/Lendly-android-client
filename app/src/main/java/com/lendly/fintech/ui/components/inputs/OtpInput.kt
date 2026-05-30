package com.lendly.fintech.ui.components.inputs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun OtpInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
    isError: Boolean = false,
    onComplete: (String) -> Unit = {},
) {
    BasicTextField(
        value = value,
        onValueChange = { new ->
            if (new.length <= length && new.all { it.isDigit() }) {
                onValueChange(new)
                if (new.length == length) onComplete(new)
            }
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(length) { index ->
                    OtpBox(
                        digit = value.getOrNull(index)?.toString() ?: "",
                        isFocused = index == value.length && value.length < length,
                        isError = isError,
                        isComplete = value.length == length,
                    )
                }
            }
        }
    )
}

@Composable
private fun OtpBox(
    digit: String,
    isFocused: Boolean,
    isError: Boolean,
    isComplete: Boolean,
) {
    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isComplete -> MaterialTheme.colorScheme.primaryContainer
        isFocused -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.medium,
            )
    ) {
        Text(
            text = digit,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(name = "OTP - Empty")
@Composable
private fun OtpEmptyPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            OtpInput(value = "", onValueChange = {})
        }
    }
}

@Preview(name = "OTP - Partial")
@Composable
private fun OtpPartialPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            OtpInput(value = "123", onValueChange = {})
        }
    }
}

@Preview(name = "OTP - Complete")
@Composable
private fun OtpCompletePreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            OtpInput(value = "123456", onValueChange = {})
        }
    }
}

@Preview(name = "OTP - Error")
@Composable
private fun OtpErrorPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            OtpInput(value = "123", onValueChange = {}, isError = true)
        }
    }
}
