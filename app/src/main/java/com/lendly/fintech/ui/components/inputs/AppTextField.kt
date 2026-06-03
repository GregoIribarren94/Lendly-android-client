package com.lendly.fintech.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.BackgroundCircleNeutral
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
    shape: Shape = RoundedCornerShape(8.dp),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label.takeIf { it.isNotEmpty() }?.let { { Text(text = it) } },
        modifier = modifier.fillMaxWidth(),
        placeholder = placeholder?.let { { Text(text = it) } },
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage) }
        } else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BackgroundCircleNeutral,
            unfocusedBorderColor = BackgroundCircleNeutral,
            errorBorderColor = Color.Red,
            focusedLabelColor = Color(0xFF454745),
            unfocusedLabelColor = Color(0xFF454745)
        )
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
