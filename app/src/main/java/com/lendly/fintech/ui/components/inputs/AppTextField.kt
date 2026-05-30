package com.lendly.fintech.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        placeholder = placeholder?.let { { Text(text = it) } },
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage, style = MaterialTheme.typography.bodySmall) }
        } else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
    )
}

@Preview(name = "TextField - Normal")
@Composable
private fun TextFieldNormalPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            AppTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                placeholder = "ejemplo@mail.com",
            )
        }
    }
}

@Preview(name = "TextField - Error")
@Composable
private fun TextFieldErrorPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            AppTextField(
                value = "correo-invalido",
                onValueChange = {},
                label = "Email",
                isError = true,
                errorMessage = "El email no es válido",
            )
        }
    }
}

@Preview(name = "TextField - With value")
@Composable
private fun TextFieldWithValuePreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            AppTextField(
                value = "usuario@lendly.com",
                onValueChange = {},
                label = "Email",
            )
        }
    }
}
